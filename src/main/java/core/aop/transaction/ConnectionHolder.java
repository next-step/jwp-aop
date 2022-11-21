package core.aop.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {

    private static final ThreadLocal<Connection> threadLocal = ThreadLocal.withInitial(() -> null);

    private ConnectionHolder() {
    }

    public static ConnectionHolder getInstance() {
        return Cache.CONNECTION_HOLDER;
    }

    public boolean hasConnection() {
        return getConnection() != null;
    }

    public void setConnection(Connection connection) {
        threadLocal.set(connection);
    }

    public Connection getConnection() {
        return threadLocal.get();
    }

    public void clear() {
        threadLocal.remove();
    }

    private static class Cache {
        private static final ConnectionHolder CONNECTION_HOLDER = new ConnectionHolder();
    }
}
