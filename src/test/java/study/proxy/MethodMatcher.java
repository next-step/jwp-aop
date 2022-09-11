package study.proxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass, Object[] arguments);
}
