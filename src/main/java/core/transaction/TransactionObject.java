package core.transaction;

import org.springframework.core.NamedThreadLocal;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionObject {

    private static final ThreadLocal<TransactionObject> TRANSACTION_HOLDER = new NamedThreadLocal<>("current transaction");
    private final Connection connection;
    private final TransactionObject oldTransactionInfo;

    private TransactionObject(Connection connection) {
        Assert.notNull(connection, "connectionHolder must not be null");
        this.connection = connection;
        this.oldTransactionInfo = TRANSACTION_HOLDER.get();
        TRANSACTION_HOLDER.set(this);
    }

    public static TransactionObject from(Connection connection) {
        return new TransactionObject(connection);
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
            TRANSACTION_HOLDER.remove();
        } catch (SQLException e) {
            throw new TransactionException("close failed", e);
        }
    }

    public boolean isNew() {
        return oldTransactionInfo == null;
    }

    public void restoreTransaction() {
        TRANSACTION_HOLDER.set(this.oldTransactionInfo);
    }
}
