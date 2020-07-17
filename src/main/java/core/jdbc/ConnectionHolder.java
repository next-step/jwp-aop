package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class ConnectionHolder {

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    public static boolean hasConnection() {
        return Optional.ofNullable(THREAD_LOCAL.get()).map(connection -> {
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                return false;
            }
        }).orElse(false);
    }

    public void setConnection(Connection connection) {
        THREAD_LOCAL.set(connection);
    }

    public static Connection getConnection() {
        return THREAD_LOCAL.get();
    }
}
