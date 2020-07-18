package core.aop.transaction;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.Pointcut;

class TransactionAdvisor implements Advisor {

    @Override
    public Advice getAdvice() {
        return new TransactionAdvice();
    }

    @Override
    public Pointcut getPointcut() {
        return new TransactionPointcut();
    }
}
