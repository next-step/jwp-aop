package study.proxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodPredicate {
    boolean matches(Method method, Object[] args);
}
