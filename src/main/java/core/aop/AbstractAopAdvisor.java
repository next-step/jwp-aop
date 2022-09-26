package core.aop;

import java.lang.reflect.Method;

public abstract class AbstractAopAdvisor implements Advisor {

    private final Advice advice;
    private final Pointcut pointcut;

    public AbstractAopAdvisor(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public boolean matches(Method method, Class<?> target) {
        return pointcut.matches(method, target);
    }

    public Object invoke(Object object, Method method, Object[] args) {
        return advice.invoke(object, method, args);
    }

    @Override
    public Advice advice() {
        return advice;
    }

    @Override
    public Pointcut pointcut() {
        return pointcut;
    }
}
