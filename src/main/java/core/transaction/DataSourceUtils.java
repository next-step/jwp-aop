package core.transaction;

import core.jdbc.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DataSourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);

    private DataSourceUtils() {
        throw new AssertionError(String.format("%s can not be instanced", getClass()));
    }

    public static Connection connection(DataSource dataSource) {
        return TransactionResourceManager.resource(dataSource)
                .map(Connection.class::cast)
                .orElseGet(() -> fetchConnection(dataSource));
    }

    private static Connection fetchConnection(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            TransactionResourceManager.bindResource(dataSource, connection);
            return connection;
        } catch (SQLException e) {
            throw new DataAccessException(String.format("DataSource returned null from getConnection(): %s", dataSource), e);
        }
    }

    public static void releaseConnection(Connection con, DataSource dataSource) {
        try {
            TransactionResourceManager.unbindResource(dataSource);
            con.close();
        } catch (SQLException e) {
            logger.debug("failed release connection", e);
        }
    }
}
