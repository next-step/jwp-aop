package core.aop.transactional;

import core.aop.advice.Advice;
import core.aop.advice.MethodInvocation;
import core.jdbc.DataAccessException;
import core.util.DataSourceUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
public class TransactionalAdvice implements Advice {
    public static final String TRANSACTION_START_MESSAGE = "transaction start";
    public static final String TRANSACTION_COMMIT_MESSAGE = "transaction commit";
    public static final String TRANSACTION_ROLLBACK_MESSAGE = "transaction rollback";
    public static final String TRANSACTION_END_MESSAGE = "transaction end";

    private final DataSource dataSource;

    public TransactionalAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug(TRANSACTION_START_MESSAGE);
        Connection connection = DataSourceUtil.getConnection(dataSource);

        try {
            connection.setAutoCommit(false);
            Object proceed = invocation.proceed();
            connection.commit();
            log.debug(TRANSACTION_COMMIT_MESSAGE);
            return proceed;
        }
        catch (RuntimeException e) {
            connection.rollback();
            log.debug(TRANSACTION_ROLLBACK_MESSAGE);
            log.error(e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            connection.close();
            DataSourceUtil.removeConnection();
            log.debug(TRANSACTION_END_MESSAGE);
        }
    }
}
