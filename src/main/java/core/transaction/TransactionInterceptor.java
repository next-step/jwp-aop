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
        TransactionObject transaction = newTransaction();
        try {
            transaction.setAutoCommit(false);
            Object result = joinpoint.proceed();
            transaction.commit();
            return result;
        } catch (Throwable e) {
            transaction.rollback();
            throw e;
        } finally {
            cleanupAfterCompletion(transaction);
        }
    }

    public TransactionObject newTransaction() throws TransactionException {
        return TransactionObject.from(DataSourceUtils.connection(dataSource));
    }

    private void cleanupAfterCompletion(TransactionObject transaction) {
        if (transaction.isNew()) {
            DataSourceUtils.releaseConnection(transaction.connection(), dataSource);
            transaction.clear();
            return;
        }
        transaction.restoreTransaction();
    }
}
