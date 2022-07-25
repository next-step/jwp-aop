package study.proxy;

import java.lang.reflect.Method;

public class SayPrefixMethodMatcher implements MethodMatcher{

    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        return m.getName().startsWith("say");
    }
}
