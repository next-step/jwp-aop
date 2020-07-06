package core.di.beans.factory.aop;

import java.lang.reflect.Method;

/**
 * Interface to be implemented by objects that determine whether the given method is proper candidate for advice.
 */
public interface MethodMatcher {

    /**
     * @param method candidate method
     * @param targetClass target class
     * @return true if given method is proper candidate
     */
    boolean matches(Method method, Class<?> targetClass);
}
