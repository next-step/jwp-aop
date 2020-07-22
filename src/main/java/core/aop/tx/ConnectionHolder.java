package core.aop.tx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionHolder {

    private static ThreadLocal<ThreadConnection> connectionThreadLocal = new ThreadLocal<>();

    public static boolean hasConnection() {
        return getConnection() != null;
    }

    public static void setConnection(Connection connection, boolean maintained) {
        if (getThreadConnection() == null) {

        }

        ThreadConnection threadConnection = getThreadConnection();
        threadConnection.setConnection(connection);
    }

    public static void clear() throws SQLException {
        Connection connection = getConnection();
        connection.close();
        connectionThreadLocal.remove();
    }

    public static Connection getConnection() {
        return getThreadConnection().getConnection();
    }

    private static ThreadConnection getThreadConnection() {
        return connectionThreadLocal.get();
    }

}
