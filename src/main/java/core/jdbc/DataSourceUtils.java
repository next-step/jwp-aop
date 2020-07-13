package core.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author KingCjy
 */
public class DataSourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);

    public static Connection getConnection(DataSource dataSource) {
        try {
            return doGetConnection(dataSource);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to Create JDBC Connection", e);
        }
    }

    private static Connection doGetConnection(DataSource dataSource) throws SQLException {
        if(!TransactionManager.isTransactionActive()) {
            logger.info("Start Connection Without Transaction");
            return dataSource.getConnection();
        }

        Connection connection = TransactionManager.getHoldConnection();

        if(connection != null) {
            return connection;
        }

        connection = TransactionManager.getConnection();

        if(connection != null) {
            TransactionManager.holdConnection(connection);
            return connection;
        }

        logger.info("Start Connection");
        connection = dataSource.getConnection();
        TransactionManager.registerConnection(connection);

        return connection;
    }

    public static void releaseConnection(Connection connection) {
        try {
            doReleaseConnection(connection);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to close JDBC Connection", e);
        }
    }

    private static void doReleaseConnection(Connection connection) throws SQLException {
        if(connection == null) {
            return;
        }

        Connection holderConnection = TransactionManager.getHoldConnection();

        if(holderConnection != null && connection == holderConnection) {
            TransactionManager.releaseConnection();
            return;
        }

        doCloseConnection(connection);
    }

    private static void doCloseConnection(Connection connection) throws SQLException {
        connection.close();
        logger.info("Close Connection");
    }
}
