package core.aop.support;

import core.aop.Advice;
import core.aop.Pointcut;
import core.aop.PointcutAdvisor;

public class DefaultPointcutAdvisor implements PointcutAdvisor {

    private final Pointcut pointcut;
    private final Advice advice;

    public DefaultPointcutAdvisor(Advice advice) {
        this(Pointcut.TRUE, advice);
    }

    public DefaultPointcutAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}
