package core.di.beans.factory.aop.advisor;

import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
