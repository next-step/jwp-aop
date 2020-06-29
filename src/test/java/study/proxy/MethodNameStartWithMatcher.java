package study.proxy;

import java.lang.reflect.Method;

public class MethodNameStartWithMatcher implements MethodMatcher {

    private final String namePattern;

    public MethodNameStartWithMatcher(String namePattern) {
        this.namePattern = namePattern;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] arguments) {
        return method.getName()
                .startsWith(namePattern);
    }
}
