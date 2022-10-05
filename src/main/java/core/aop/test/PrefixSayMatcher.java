package core.aop.test;

import core.aop.MethodMatcher;

import java.lang.reflect.Method;

public class PrefixSayMatcher implements MethodMatcher {
    @Override
    public boolean matches(Object target, Method method, Object[] args) {
        return method.getName().startsWith("say");
    }
}
