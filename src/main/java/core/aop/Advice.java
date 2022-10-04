package core.aop;

import core.jdbc.DataAccessException;
import java.sql.Connection;
import javax.sql.DataSource;
import net.sf.cglib.proxy.MethodProxy;
import next.support.ConnectionHolder;
import next.support.DataSourceUtils;

public interface Advice {

    Object advice(Object obj, Object[] args, MethodProxy proxy) throws Throwable;

    class UpperCaseAdvice implements Advice {

        public Object advice(Object obj, Object[] args, MethodProxy proxy) throws Throwable {
            String result = (String) proxy.invokeSuper(obj, args);

            return result.toUpperCase();
        }
    }

    class TransactionalAdvice implements Advice {

        private DataSource dataSource;

        public TransactionalAdvice(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        public Object advice(Object obj, Object[] args, MethodProxy proxy) throws Throwable {
            Connection connection = ConnectionHolder.getConnection(dataSource);
            connection.setAutoCommit(false);
            try {
                Object result = proxy.invokeSuper(obj, args);
                connection.commit();

                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new DataAccessException(e);
            } finally {
                DataSourceUtils.close();
            }
        }
    }

}
