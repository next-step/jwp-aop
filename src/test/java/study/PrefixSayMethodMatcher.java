package study;

import java.lang.reflect.Method;

public class PrefixSayMethodMatcher implements MethodMatcher {

    public static final String PREFIX_METHOD = "say";

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getName().startsWith(PREFIX_METHOD);
    }
}
