package study.proxy.example;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TextUpperCaseMethodMatcher implements MethodMatcher {
    private final String[] methodPrefix;

    public TextUpperCaseMethodMatcher(String... prefix) {
        methodPrefix = prefix;
    }

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        return Arrays.stream(methodPrefix).anyMatch(prefix -> method.getName().startsWith(prefix));
    }
}
