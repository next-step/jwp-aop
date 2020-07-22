package core.aop.tx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionHolder {

    private static ThreadLocal<ThreadConnection> connectionThreadLocal = new ThreadLocal<>();

    public static boolean hasConnection() {
        return getThreadConnection() != null;
    }

    public static void setConnection(Connection connection, boolean maintained) {
        ThreadConnection threadConnection = getThreadConnection();
        if (threadConnection == null) {
            ThreadConnection newThreadConnection = new ThreadConnection(connection, maintained);
            connectionThreadLocal.set(newThreadConnection);
            return;
        }

        threadConnection.setConnection(connection);
    }

    public static void clear() throws SQLException {
        ThreadConnection threadConnection = getThreadConnection();
        if (threadConnection == null || threadConnection.isMaintained()) {
            return;
        }

        clearCompletely();
    }

    public static void clearCompletely() throws SQLException {
        ThreadConnection threadConnection = getThreadConnection();
        if (threadConnection == null) {
            return;
        }

        threadConnection.close();
        connectionThreadLocal.remove();
    }

    public static Connection getConnection() {
        ThreadConnection threadConnection = getThreadConnection();
        if (threadConnection == null) {
            throw new IllegalStateException("Connection does not exist.");
        }

        return threadConnection.getConnection();
    }

    private static ThreadConnection getThreadConnection() {
        return connectionThreadLocal.get();
    }

}
