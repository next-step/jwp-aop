package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> currentConnection = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> transactionActive = new ThreadLocal<>();

    private ConnectionHolder() {
    }

    public static boolean hasConnection() {
        return getConnection() != null;
    }

    public static Connection getConnection() {
        return currentConnection.get();
    }

    public static void setConnection(Connection connection, boolean active) {
        if (getConnection() != null) {
            currentConnection.remove();
        }

        setAutoCommit(connection, active);
        currentConnection.set(connection);
        transactionActive.set(active);
    }

    private static void setAutoCommit(Connection connection, boolean active) {
        try {
            connection.setAutoCommit(!active);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void releaseConnection()  {
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        connectionClose(connection);
    }

    private static void connectionClose(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            currentConnection.remove();
            transactionActive.remove();
        }
    }
}
