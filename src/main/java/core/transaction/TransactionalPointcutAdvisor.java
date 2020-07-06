package core.transaction;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

public class TransactionalPointcutAdvisor extends AbstractPointcutAdvisor {
    private final TransactionalPointcut pointcut = new TransactionalPointcut();
    private final TransactionalAdvice interceptor;

    public TransactionalPointcutAdvisor(TransactionalAdvice interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return interceptor;
    }
}
