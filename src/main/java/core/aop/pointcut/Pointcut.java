package core.aop.pointcut;

import java.lang.reflect.Method;

public interface Pointcut {
    boolean matches(Method method, Class<?> targetClass, Object[] args);
}
