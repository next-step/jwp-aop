package study.jdk;

import java.lang.reflect.Method;

/**
 * Created by youngjae.havi on 2019-09-01
 */
public interface MethodMatcher {
    boolean matches(Method m, Class targetClass, Object[] args);
}
