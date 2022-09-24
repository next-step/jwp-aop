package core.aop;

import java.lang.reflect.Method;

public interface Advisor {
    Advice advice();
    Pointcut pointcut();
    boolean matches(Method method, Class<?> target);
    Object invoke(Object object, Method method, Object[] args);
}
