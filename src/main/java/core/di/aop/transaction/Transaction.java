package core.di.aop.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transaction implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

    private final Connection connection;
    private boolean committed;

    public Transaction(final Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.connection = connection;
    }

    public void commit() throws SQLException {
        connection.commit();
        committed = true;
        logger.debug("committed");
    }

    public boolean canRollback() {
        return !committed;
    }

    @Override
    public void close() throws SQLException {
        if (canRollback()) {
            logger.debug("rollback");
            connection.rollback();
        }
    }
}
