package core.di.beans.factory.aop;

import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
