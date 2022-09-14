package core.transaction;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {

    private static final ThreadLocal<ConnectionHolder> CONNECTION_HOLDER_THREAD_LOCAL = new NamedThreadLocal<>("current connection");
    private final Connection connection;
    private final ConnectionHolder oldConnectionInfo;

    private ConnectionHolder(Connection connection) {
        Assert.notNull(connection, "'connection' must not be null");
        this.connection = connection;
        this.oldConnectionInfo = CONNECTION_HOLDER_THREAD_LOCAL.get();
        CONNECTION_HOLDER_THREAD_LOCAL.set(this);
    }

    public static ConnectionHolder from(Connection connection) {
        return new ConnectionHolder(connection);
    }

    public Connection connection() {
        return connection;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionException("commit failed", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionException("rollback failed", e);
        }
    }

    public void clear() {
        try {
            connection.close();
            CONNECTION_HOLDER_THREAD_LOCAL.remove();
        } catch (SQLException e) {
            throw new TransactionException("close failed", e);
        }
    }

    public boolean isNew() {
        return oldConnectionInfo == null;
    }

    public void restore() {
        CONNECTION_HOLDER_THREAD_LOCAL.set(this.oldConnectionInfo);
    }
}
