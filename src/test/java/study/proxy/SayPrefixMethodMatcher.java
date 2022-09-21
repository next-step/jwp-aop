package study.proxy;

import core.di.beans.factory.aop.advisor.MethodMatcher;

import java.lang.reflect.Method;

public class SayPrefixMethodMatcher implements MethodMatcher {
    public static final String PASS_PREFIX = "say";

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return  method.getName().startsWith(PASS_PREFIX);
    }
}
