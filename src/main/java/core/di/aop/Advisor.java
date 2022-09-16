package core.di.aop;

public interface Advisor {

    Advice getAdvice();

    Pointcut getPointcut();

}
