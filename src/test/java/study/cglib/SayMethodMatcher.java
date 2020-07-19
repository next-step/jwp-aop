package study.cglib;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {

    private static final String SAY_METHOD_PREFIX = "say";

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getName().startsWith(SAY_METHOD_PREFIX);
    }
}
