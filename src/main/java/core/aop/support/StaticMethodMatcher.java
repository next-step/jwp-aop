package core.aop.support;

import java.lang.reflect.Method;

import core.aop.MethodMatcher;

public abstract class StaticMethodMatcher implements MethodMatcher {

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        throw new UnsupportedOperationException();
    }
}
