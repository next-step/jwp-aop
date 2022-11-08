package core.di.beans.factory.proxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface Pointcut {

    boolean matches(Class<?> targetClass, Method method);
}
