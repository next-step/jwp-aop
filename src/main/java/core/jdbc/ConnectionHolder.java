package core.jdbc;

import java.sql.Connection;
import java.util.Objects;

public class ConnectionHolder extends ThreadLocal<Connection> {
    public boolean hasConnection() {
        return Objects.nonNull(get());
    }

    public void setConnection(Connection connection) {
        set(connection);
    }

    public Connection getConnection() {
        return get();
    }

    public void removeConnection() {
        remove();
    }
}
