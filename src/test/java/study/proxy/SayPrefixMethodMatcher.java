package study.proxy;

import java.lang.reflect.Method;

public class SayPrefixMethodMatcher implements MethodMatcher {
    public static final String PASS_PREFIX = "say";

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        return  method.getName().startsWith(PASS_PREFIX);
    }
}
