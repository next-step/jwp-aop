package core.aop;

import java.lang.reflect.Method;

public interface Pointcut {

    boolean matches(Class<?> targetClass);

    boolean matches(Method method);
}
