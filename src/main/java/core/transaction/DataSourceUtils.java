package core.transaction;

import core.jdbc.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);

    private DataSourceUtils() {}

    public static Connection getConnection(DataSource dataSource) {
        return TransactionResourceManager.getResource(dataSource)
                .map(Connection.class::cast)
                .orElseGet(() -> fetchConnection(dataSource));
    }

    private static Connection fetchConnection(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            TransactionResourceManager.bindResource(dataSource, connection);
            return connection;
        } catch (SQLException e) {
            throw new DataAccessException("DataSource Not Accessed", e);
        }
    }

    public static void releaseConnection(Connection connection, DataSource dataSource) {
        try {
            TransactionResourceManager.unbindResource(dataSource);
            connection.close();
        } catch (SQLException e) {
            logger.debug("fail release connection", e);
        }
    }
}
