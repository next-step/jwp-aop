package core.di.beans.factory.support.proxy.example;

import core.di.beans.factory.proxy.MethodMatcher;

import java.lang.reflect.Method;
import java.util.Arrays;

public class NameMatchMethodPointcut implements MethodMatcher {
    private final String[] methodNamePrefix;

    public NameMatchMethodPointcut(String... prefix) {
        methodNamePrefix = prefix;
    }

    @Override
    public boolean matches(Method method, Class targetClass) {
        return Arrays.stream(methodNamePrefix).anyMatch(prefix -> method.getName().startsWith(prefix));
    }
}
