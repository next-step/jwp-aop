package core.aop.support;

import java.sql.Connection;

import javax.sql.DataSource;

import core.aop.intercept.MethodInterceptor;
import core.aop.intercept.MethodInvocation;
import core.jdbc.DataAccessException;
import core.jdbc.datasource.DataSourceUtils;

public class TransactionAdvice implements MethodInterceptor {

    private final DataSource dataSource;

    public TransactionAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Connection connection = DataSourceUtils.getConnection(dataSource, true);
        try {
            Object result = invocation.proceed();
            connection.commit();
            return result;
        } catch (Exception e) {
            connection.rollback();
            throw new DataAccessException(e);
        } finally {
            DataSourceUtils.releaseConnection();
        }
    }
}
