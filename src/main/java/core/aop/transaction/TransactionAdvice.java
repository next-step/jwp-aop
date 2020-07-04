package core.aop.transaction;

import core.aop.Advice;
import core.aop.MethodInvocation;
import core.di.beans.factory.BeanFactory;
import core.jdbc.DataAccessException;
import core.jdbc.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by yusik on 2020/07/04.
 */
public class TransactionAdvice implements Advice {

    private final BeanFactory beanFactory;

    public TransactionAdvice(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Connection connection = DataSourceUtils.getConnection(beanFactory.getBean(DataSource.class));

        try {
            connection.setAutoCommit(false);
            Object ret = invocation.proceed();
            connection.commit();
            return ret;
        } catch (SQLException e) {
            connection.rollback();
            throw new DataAccessException(e);
        } finally {
            connection.close();
            DataSourceUtils.releaseConnection();
        }

    }
}
