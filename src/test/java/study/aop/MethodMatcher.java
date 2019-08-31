package study.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Method m, Object targetClass, Object[] args);
}
