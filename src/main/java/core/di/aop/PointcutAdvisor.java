package core.di.aop;

import java.lang.reflect.Method;

public class PointcutAdvisor {

    private final Advice advice;
    private final Pointcut pointcut;

    public PointcutAdvisor(final Advice advice, final Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return advice.invoke(methodInvocation);
    }

    public boolean matches(Method method, Class<?> targetClass) {
        return pointcut.matches(method, targetClass);
    }
}
