package core.aop.tx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionHolder {

    private static ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    public static ConnectionHolder create() {
        return new ConnectionHolder();
    }

    public boolean hasConnection() {
        return CONNECTION_THREAD_LOCAL.get() != null;
    }

    public void setConnection(Connection connection) {
        CONNECTION_THREAD_LOCAL.set(connection);
    }

    public void clear() {
        CONNECTION_THREAD_LOCAL.remove();
    }

    public static Connection getConnection() {
        return CONNECTION_THREAD_LOCAL.get();
    }

}
