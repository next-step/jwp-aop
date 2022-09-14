package core.transaction;

import core.aop.Advice;
import core.aop.Joinpoint;
import org.springframework.transaction.TransactionException;
import org.springframework.util.Assert;

import javax.sql.DataSource;

public class TransactionInterceptor implements Advice {

    private final DataSource dataSource;

    private TransactionInterceptor(DataSource dataSource) {
        Assert.notNull(dataSource, "dataSource must not be null");
        this.dataSource = dataSource;
    }

    public static TransactionInterceptor from(DataSource dataSource) {
        return new TransactionInterceptor(dataSource);
    }

    @Override
    public Object invoke(Joinpoint joinpoint) throws Throwable {
        ConnectionHolder connection = newConnection();
        try {
            connection.setAutoCommit(false);
            Object result = joinpoint.proceed();
            connection.commit();
            return result;
        } catch (Throwable e) {
            connection.rollback();
            throw e;
        } finally {
            cleanupAfterCompletion(connection);
        }
    }

    public ConnectionHolder newConnection() throws TransactionException {
        return ConnectionHolder.from(DataSourceUtils.connection(dataSource));
    }

    private void cleanupAfterCompletion(ConnectionHolder connectionHolder) {
        if (connectionHolder.isNew()) {
            DataSourceUtils.releaseConnection(connectionHolder.connection(), dataSource);
            connectionHolder.clear();
            return;
        }
        connectionHolder.restore();
    }
}
