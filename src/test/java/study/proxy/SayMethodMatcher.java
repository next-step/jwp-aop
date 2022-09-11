package study.proxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface SayMethodMatcher {

    boolean matches(Method method, Class<?> targetClass, Object[] arguments);
}
