package core.aop.support;

import javax.sql.DataSource;

import core.aop.Advice;
import core.aop.Pointcut;
import core.aop.PointcutAdvisor;

public class TransactionAdvisor implements PointcutAdvisor {

    private final TransactionPointcut pointcut;
    private final TransactionAdvice advice;

    public TransactionAdvisor(DataSource dataSource) {
        this.pointcut = new TransactionPointcut();
        this.advice = new TransactionAdvice(dataSource);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
