package core.aop;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
