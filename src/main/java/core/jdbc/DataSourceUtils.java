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
    public static Connection getConnection(DataSource dataSource) {
        try {
            return doGetConnection(dataSource);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to Create JDBC Connection", e);
        }
    }

    private static Connection doGetConnection(DataSource dataSource) throws SQLException {
        if(!TransactionManager.isTransactionActive()) {
            return dataSource.getConnection();
        }

        Connection connection = TransactionManager.getHoldConnection();

        if(connection != null) {
            return connection;
        }

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
            TransactionManager.releaseHoldConnection();;
            return;
        }

        doCloseConnection(connection);
    }

    private static void doCloseConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
