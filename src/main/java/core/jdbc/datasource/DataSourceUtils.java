package core.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.CannotGetJdbcConnectionException;

public abstract class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        return getConnection(dataSource, false);
    }

    public static Connection getConnection(DataSource dataSource, boolean transactionActive) throws CannotGetJdbcConnectionException {
        try {
            return doGetConnection(dataSource, transactionActive);
        } catch (SQLException ex) {
            throw new CannotGetJdbcConnectionException("Failed to obtain JDBC Connection", ex);
        }
    }

    public static Connection doGetConnection(DataSource dataSource, boolean transactionActive) throws SQLException {
        if (!ConnectionHolder.hasConnection()) {
            ConnectionHolder.setConnection(fetchConnection(dataSource), transactionActive);
        }
        return ConnectionHolder.getConnection();
    }

    private static Connection fetchConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void releaseConnectionDependingOnTransactionActivation() {
        try {
            ConnectionHolder.releaseDependingOnTransactionActivation();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void releaseConnection() throws SQLException {
        ConnectionHolder.releaseConnection();
    }
}
