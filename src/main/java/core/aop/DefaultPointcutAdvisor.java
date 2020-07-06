package core.aop;

import org.springframework.aop.Pointcut;

public class DefaultPointcutAdvisor implements PointcutAdvisor {

    private final Pointcut pointcut;

    public DefaultPointcutAdvisor(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
