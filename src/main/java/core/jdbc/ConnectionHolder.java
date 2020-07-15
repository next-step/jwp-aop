package core.jdbc;

import java.sql.Connection;
import java.util.Objects;

public class ConnectionHolder {
    private ThreadLocal<Connection> connection = new ThreadLocal<>();

    public boolean hasConnection() {
        return Objects.nonNull(connection.get());
    }
    public void setConnection(Connection connection) {
        this.connection.set(connection);
    }

    public Connection getConnection() {
        return connection.get();
    }
}
