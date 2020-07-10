package core.transaction;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Slf4j
public class TransactionalAdvice implements MethodInterceptor {
    private PlatformTransactionManager transactionManager;

    public TransactionalAdvice(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("transaction start");
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object ret = invocation.proceed();
            this.transactionManager.commit(status);
            return ret;
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
            this.transactionManager.rollback(status);
            throw e;
        }
        finally {
            log.debug("transaction end");
        }
    }
}
