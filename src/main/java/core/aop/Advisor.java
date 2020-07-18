package core.aop;


public interface Advisor {

    Advice getAdvice();

    Pointcut getPointcut();
}
