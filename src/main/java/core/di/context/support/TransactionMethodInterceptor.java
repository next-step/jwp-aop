package core.di.context.support;

import core.di.context.ApplicationContext;
import core.jdbc.ConnectionHolder;
import core.jdbc.DataSourceUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionMethodInterceptor implements MethodInterceptor {

    private ApplicationContext applicationContext;
    private TransactionMetadata metadata;

    public TransactionMethodInterceptor(ApplicationContext applicationContext,
                                        TransactionMetadata metadata) {
        this.applicationContext = applicationContext;
        this.metadata = metadata;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!metadata.isTransaction(method)) {
            return proxy.invokeSuper(obj, args);
        }

        DataSource dataSource = applicationContext.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        Object result = null;
        try {
            ConnectionHolder.set(connection);
            result = proxy.invokeSuper(obj, args);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
            DataSourceUtils.connectionClear();
        }

        return result;
    }

}
