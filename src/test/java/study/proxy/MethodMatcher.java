package study.proxy;

import java.lang.reflect.Method;

public interface MethodMatcher {
    String TARGET_METHOD_NAME_PREFIX = "say";

    default boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getReturnType().equals(String.class) && method.getName().startsWith(TARGET_METHOD_NAME_PREFIX);
    }
}
