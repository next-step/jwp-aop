package aop;

import java.lang.reflect.Method;

public class MethodMatcherSay implements MethodMatcher {

    private static final String startMatcher = "say";

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        if (method.getName().startsWith(startMatcher)) {
            return true;
        }
        return false;
    }
}
