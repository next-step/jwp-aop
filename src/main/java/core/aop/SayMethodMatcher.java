package core.aop;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getName().startsWith("say");
    }
}
