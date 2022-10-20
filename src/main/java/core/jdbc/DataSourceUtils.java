package core.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {
    private DataSourceUtils() {
    }

    public static void setConnection(Connection connection) {
        ConnectionHolder.setConnection(connection);
    }
    
    public static Connection getConnection(DataSource dataSource) {
        if (ConnectionHolder.hasConnection()) {
            return ConnectionHolder.getConnection();
        }
        return fetchConnection(dataSource);
    }

    private static Connection fetchConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void releaseConnection() {
        ConnectionHolder.releaseConnection();
    }

    public static void releaseConnection(Connection currentConnection) {
        if (currentConnection == ConnectionHolder.getConnection()) {
            return;
        }

        close(currentConnection);
    }

    private static void close(Connection currentConnection) {
        try {
            currentConnection.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
