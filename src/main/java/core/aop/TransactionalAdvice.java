package core.aop;

import core.jdbc.DataAccessException;
import core.jdbc.DataSourceUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionalAdvice implements Advice {
    private final DataSource dataSource;

    public TransactionalAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        DataSourceUtils.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            Object result = method.invoke(object, args);
            connection.commit();
            return result;
        } catch (Throwable e) {
            rollback(connection);
            throw new DataAccessException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection);
        }
    }

    private static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
