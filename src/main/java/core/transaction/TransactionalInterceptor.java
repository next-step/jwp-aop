package core.transaction;

import core.aop.Advice;
import core.aop.Joinpoint;

import javax.sql.DataSource;

public class TransactionalInterceptor implements Advice {

    private final DataSource dataSource;

    public TransactionalInterceptor(DataSource dataSource) {
        this.dataSource = dataSource;
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
            clear(connection);
        }
    }

    public ConnectionHolder newConnection() {
        return new ConnectionHolder(DataSourceUtils.getConnection(dataSource));
    }

    public void clear(ConnectionHolder connectionHolder) {
        if (connectionHolder.isNew()) {
            DataSourceUtils.releaseConnection(connectionHolder.getConnection(), dataSource);
            connectionHolder.clear();
            return;
        }
        connectionHolder.restore();
    }
}
