package core.di.beans.factory.proxy;

import java.lang.reflect.Method;

public class Advisor {

    private final Pointcut pointcut;
    private final Advice advice;

    public Advisor(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public boolean matches(Class<?> targetClass, Method method) {
        return pointcut.matches(targetClass, method);
    }

    public Object invoke(JoinPoint joinPoint) {
        return advice.invoke(joinPoint);
    }
}
