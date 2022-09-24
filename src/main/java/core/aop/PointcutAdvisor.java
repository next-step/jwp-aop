package core.aop;

import java.lang.reflect.Method;

public class PointcutAdvisor implements Advisor {

    private final Advice advice;
    private final Pointcut pointcut;

    public PointcutAdvisor(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    @Override
    public Advice advice() {
        return this.advice;
    }

    @Override
    public Pointcut pointcut() {
        return pointcut;
    }

    @Override
    public boolean matches(Method method, Class<?> target) {
        return pointcut.matches(method, target);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        return advice.invoke(object, method, args);
    }
}
