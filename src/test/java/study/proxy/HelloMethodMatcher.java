package study.proxy;

import java.lang.reflect.Method;

public class HelloMethodMatcher implements MethodMatcher {

    private final String expression;

    public HelloMethodMatcher(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        String methodName = method.getName();
        return methodName.startsWith(expression);
    }
}
