package core.aop;

import java.lang.reflect.Method;

/**
 * Created by yusik on 2020/07/03.
 */
@FunctionalInterface
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
