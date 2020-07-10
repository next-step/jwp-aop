package core.aop.advisor;

import core.aop.advice.Advice;
import core.aop.pointcut.Pointcut;
import lombok.Getter;

public class DefaultAdvisor implements Advisor {
    @Getter
    private final Pointcut pointcut;

    @Getter
    private final Advice advice;

    public DefaultAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }
}
