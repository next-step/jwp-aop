package core.aop;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

public final class Advisor {

    private final Advice advice;
    private final Pointcut pointcut;

    private Advisor(Advice advice, Pointcut pointcut) {
        Assert.notNull(advice, "`advice` must not be null");
        Assert.notNull(pointcut, "`pointcut` must not be null");
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public static Advisor of(Advice advice, Pointcut pointcut) {
        return new Advisor(advice, pointcut);
    }

    public boolean matches(Class<?> targetClass, Method method) {
        return pointcut.matches(targetClass, method);
    }

    public Object invoke(Joinpoint joinpoint) throws Throwable {
        return advice.invoke(joinpoint);
    }
}
