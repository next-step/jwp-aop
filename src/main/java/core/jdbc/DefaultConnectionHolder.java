package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DefaultConnectionHolder implements ConnectionHolder {
    private Connection connection;

    public DefaultConnectionHolder(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean hasConnection() {
        try {
            return Objects.nonNull(connection) && !connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setConnection(Connection conn) {
        this.connection = conn;
    }
}
