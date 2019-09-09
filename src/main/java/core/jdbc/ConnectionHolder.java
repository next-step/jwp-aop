package core.jdbc;

import java.sql.Connection;

public class ConnectionHolder {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection get() {
        return threadLocal.get();
    }

    public static void set(Connection connection) {
        threadLocal.set(connection);
    }

    public static void clear() {
        threadLocal.remove();
    }

}
