package core.aop;

import core.jdbc.DataAccessException;
import core.jdbc.DataSourceUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionAdvice implements Advice {

    private final DataSource dataSource;

    public TransactionAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        Connection connection = DataSourceUtils.getConnection(dataSource, true);
        try {
            Object result = method.invoke(object, args);
            connection.commit();
            return result;
        } catch (Exception e) {
            connectionRollback(connection, e);
            throw new DataAccessException(e);
        } finally {
            DataSourceUtils.releaseConnection();
        }
    }

    private static void connectionRollback(Connection connection, Exception e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new DataAccessException(e);
        }
    }
}
