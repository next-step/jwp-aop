package study.hello;

import java.lang.reflect.Method;

interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass, Object[] args);
}
