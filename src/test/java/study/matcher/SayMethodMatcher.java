package study.matcher;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        return m.getName().startsWith("say");

    }
}
