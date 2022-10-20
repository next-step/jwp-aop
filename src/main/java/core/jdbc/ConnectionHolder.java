package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private ConnectionHolder() {
    }

    public static boolean hasConnection() {
        return CONNECTION_THREAD_LOCAL.get() != null;
    }

    public static void setConnection(Connection connection) {
        CONNECTION_THREAD_LOCAL.set(connection);
    }

    public static Connection getConnection() {
        return CONNECTION_THREAD_LOCAL.get();
    }

    public static void releaseConnection() {
        if (hasConnection()) {
            release();
        }
    }

    private static void release() {
        try {
            CONNECTION_THREAD_LOCAL.get().close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        CONNECTION_THREAD_LOCAL.remove();
    }
}
