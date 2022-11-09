package core.transaction;

import org.springframework.core.NamedThreadLocal;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {
    private static final ThreadLocal<ConnectionHolder> CONNECTION_HOLDER_THREAD_LOCAL = new NamedThreadLocal<>("current connection");
    private final Connection connection;
    private final ConnectionHolder connectionHolder;

    public ConnectionHolder(Connection connection) {
        this.connection = connection;
        this.connectionHolder = CONNECTION_HOLDER_THREAD_LOCAL.get();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("commit failed", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("rollback failed", e);
        }
    }

    public void clear() {
        try {
            connection.close();
            CONNECTION_HOLDER_THREAD_LOCAL.remove();
        } catch (SQLException e) {
            throw new RuntimeException("close failed", e);
        }
    }

    public boolean isNew() {
        return connectionHolder == null;
    }

    public void restore() {
        CONNECTION_HOLDER_THREAD_LOCAL.set(this.connectionHolder);
    }
}
