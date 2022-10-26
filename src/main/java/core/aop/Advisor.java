package core.aop;

import java.lang.reflect.Method;

/**
 * 스프링 AOP에서만 사용되는 용어로 advice + pointcut 한 쌍
 */
public class Advisor {
    private final Advice advice;
    private final Pointcut pointcut;

    public Advisor(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public boolean matches(Class<?> targetClass, Method method) {
        return pointcut.matches(targetClass, method);
    }

    public Object invoke(Joinpoint joinpoint) throws Throwable {
        return advice.invoke(joinpoint);
    }
}
