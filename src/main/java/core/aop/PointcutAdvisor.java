package core.aop;

import java.lang.reflect.Method;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

    default boolean classMatches(Class<?> clazz) {
        return getPointcut().getClassFilter().matches(clazz);
    }

    default boolean methodMatches(Method method, Class<?> clazz) {
        return getPointcut().getMethodMatcher().matches(method, clazz);
    }
}
