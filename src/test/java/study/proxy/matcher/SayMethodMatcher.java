package study.proxy.matcher;

import core.aop.pointcut.MethodMatcher;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {

    private static final String SAY_PREFIX = "say";

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        String methodName = method.getName();

        return methodName.startsWith(SAY_PREFIX);
    }

}
