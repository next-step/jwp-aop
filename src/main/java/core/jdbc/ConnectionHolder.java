package core.jdbc;

import java.sql.Connection;
import java.util.Objects;

public class ConnectionHolder {

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    public boolean hasConnection() {
        return Objects.nonNull(THREAD_LOCAL.get());
    }

    public void setConnection(Connection connection) {
        THREAD_LOCAL.set(connection);
    }

    public Connection getConnection() {
        return THREAD_LOCAL.get();
    }
}
