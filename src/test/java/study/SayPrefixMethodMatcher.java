package study;

import java.lang.reflect.Method;

/**
 * Created by hspark on 2019-09-04.
 */
public class SayPrefixMethodMatcher implements MethodMatcher {

    private static final String METHOD_PREFIX = "say";

    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        return m.getName().startsWith(METHOD_PREFIX);
    }
}
