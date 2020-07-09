package core.aop.advisor;

import core.aop.advice.Advice;
import core.aop.pointcut.Pointcut;

public interface Advisor {
    Advice getAdvice();
    Pointcut getPointcut();
}
