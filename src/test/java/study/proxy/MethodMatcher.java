package study.proxy;

import java.lang.reflect.Method;

/**
 * @author : yusik
 * @date : 01/09/2019
 */
@FunctionalInterface
public interface MethodMatcher {
    boolean matches(Method method, Class target, Object[] args);
}
