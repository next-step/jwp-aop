package study.proxy;

import java.lang.reflect.Method;

public class DefaultSayMethodMatcher implements SayMethodMatcher {

    private static final String PROXY_METHOD_PREFIX = "say";

    @Override
    public boolean matches(final Method method, final Class<?> targetClass, final Object[] arguments) {
        return isMatch(method.getName());
    }

    private boolean isMatch(final String methodName) {
        return methodName.startsWith(PROXY_METHOD_PREFIX);
    }
}
