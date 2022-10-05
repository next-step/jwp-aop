package core.aop;

import java.lang.reflect.Method;

public interface Advisor {
    Advice advice();
    Pointcut pointcut();

    boolean matches(Method method, Class<?> target);

    Object invoke(Object proxy, Method method, Object[] args);
}
