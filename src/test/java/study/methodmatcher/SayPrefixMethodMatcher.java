package study.methodmatcher;

import java.lang.reflect.Method;

public class SayPrefixMethodMatcher implements MethodMatcher{

    private static final String TARGET_PREFIX = "say";

    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        if (m.getName().startsWith(TARGET_PREFIX))
            return true;
        return false;
    }
}
