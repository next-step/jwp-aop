package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private ConnectionHolder() {
        throw new AssertionError();
    }

    public static void setConnection(final Connection connection) {
        CONNECTION_THREAD_LOCAL.set(connection);
    }

    public static boolean hasConnection() {
        return CONNECTION_THREAD_LOCAL.get() != null;
    }

    public static Connection getConnection() {
        if (hasConnection() && !isClosed()) {
            return CONNECTION_THREAD_LOCAL.get();
        }

        return null;
    }

    private static boolean isClosed() {
        try {
            return CONNECTION_THREAD_LOCAL.get().isClosed();
        } catch (SQLException e) {
            throw new DataAccessException("fail check close", e);
        }
    }

    public static void releaseConnection() {
        if (hasConnection()) {
            close();
        }
    }

    private static void close() {
        try {
            CONNECTION_THREAD_LOCAL.get().close();
        } catch (SQLException e) {
            throw new DataAccessException("fail close", e);
        }
        CONNECTION_THREAD_LOCAL.remove();
    }
}
