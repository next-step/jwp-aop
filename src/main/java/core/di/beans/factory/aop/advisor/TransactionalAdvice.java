package core.di.beans.factory.aop.advisor;

import core.util.DataSourceUtils;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;

public class TransactionalAdvice implements Advice {
    private static final Logger log = LoggerFactory.getLogger(TransactionalAdvice.class);

    private DataSource dataSource;

    public TransactionalAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("start transaction advice in method: {}", method.getName());

        Connection conn = null;
        Object result = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            conn.setAutoCommit(false);

            result = proxy.invokeSuper(obj, args);
            conn.commit();

        } catch (Exception e) {
            log.error("트랜잭션이 롤백되었습니다. cause:{}", e.getMessage());

            conn.rollback();
        } finally {
            conn.close();
            DataSourceUtils.invalidate();
        }

        return result;
    }
}
