package study.aop;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        return targetClass.getSimpleName().startsWith("HelloTarget") && method.getName().startsWith("say");
    }
}
