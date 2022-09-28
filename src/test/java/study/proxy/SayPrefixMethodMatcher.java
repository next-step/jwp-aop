package study.proxy;

import java.lang.reflect.Method;

public class SayPrefixMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method method) {
        return method.getName().startsWith("say");
    }
}
