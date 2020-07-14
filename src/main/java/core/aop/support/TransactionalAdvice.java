package core.aop.support;

import core.aop.Advice;
import core.jdbc.DataAccessException;
import core.jdbc.DataSourceUtils;
import core.jdbc.TransactionManager;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author KingCjy
 */
public class TransactionalAdvice implements Advice {

    private final Logger logger = LoggerFactory.getLogger(TransactionalAdvice.class);

    private DataSource dataSource;

    public TransactionalAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;

        TransactionManager.startTransaction();
        logger.info("Transaction START");
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            connection.setAutoCommit(false);

            result = proxy.invokeSuper(obj, args);

            connection.commit();
            logger.info("Transaction COMMIT");
        } catch (SQLException | DataAccessException e) {
            try {
                logger.error(e.getMessage(), e);
                connection.rollback();
                logger.info("Transaction ROLLBACK");
            } catch (SQLException ex) {
                logger.error("SQLException", ex);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection);
            TransactionManager.finishTransaction();
            logger.info("Transaction FINISHED");
        }

        return result;
    }
}