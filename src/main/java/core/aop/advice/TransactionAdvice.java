package core.aop.advice;

import core.aop.Advice;
import core.jdbc.ConnectionHolder;
import core.jdbc.DataAccessException;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionAdvice implements Advice {

    @Override
    public Object doAdvice(Object object, Method method, Object[] arguments, MethodProxy proxy) {
        Connection connection = ConnectionHolder.getConnectionForTransaction();

        Object ret;
        try {
            connection.setAutoCommit(false);

            ret = proxy.invokeSuper(object, arguments);

            connection.commit();
        } catch (Throwable throwable) {
            rollback(connection);
            throw new DataAccessException("Fail to execute query : " + throwable.getMessage());
        } finally {
            close(connection);
        }

        return ret;
    }

    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new DataAccessException("Fail to close : " + throwables.getMessage());
        }
        ConnectionHolder.releaseConnection();
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throw new DataAccessException("Fail to rollback : " + throwables.getMessage());
        }
    }
}
