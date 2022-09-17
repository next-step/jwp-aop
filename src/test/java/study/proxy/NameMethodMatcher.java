package study.proxy;

import java.lang.reflect.Method;

public class NameMethodMatcher implements MethodMatcher {
    private String name;

    public NameMethodMatcher(String name) {
        this.name = name;
    }

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getName().startsWith(name);
    }
}
