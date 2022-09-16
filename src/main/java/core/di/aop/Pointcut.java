package core.di.aop;

import java.lang.reflect.Method;

public interface Pointcut {

    boolean matches(Method method, Class<?> targetClass);

}
