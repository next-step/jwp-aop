package core.di.aop.transaction;

import core.di.aop.Advice;
import core.di.aop.MethodInvocation;
import core.jdbc.DataAccessException;
import core.jdbc.DataSourceUtils;
import java.sql.Connection;
import javax.sql.DataSource;

public class TransactionAdvice implements Advice {

    private final DataSource dataSource;

    public TransactionAdvice(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        try (
            final Connection connection = DataSourceUtils.getConnection(dataSource);
            final Transaction transaction = new Transaction(connection)
        ) {
            final Object proceed = methodInvocation.proceed();
            transaction.commit();
            return proceed;
        } catch (Exception e) {
            throw new DataAccessException(e);
        } finally {
            DataSourceUtils.releaseConnection();
        }
    }
}
