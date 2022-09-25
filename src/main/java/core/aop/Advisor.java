package core.aop;

public interface Advisor {
    Advice advice();
    Pointcut pointcut();
}
