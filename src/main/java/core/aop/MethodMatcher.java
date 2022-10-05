package core.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Object target, Method method, Object[] args);
}
