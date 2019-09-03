package study;

import java.lang.reflect.Method;

/**
 * Created by hspark on 2019-09-04.
 */
public interface MethodMatcher {
    boolean matches(Method m, Class targetClass, Object[] args);
}
