package core.aop;

import org.springframework.aop.Pointcut;

public interface PointcutAdvisor {
    Pointcut getPointcut();
}
