package core.aop;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {

    private static final String SAY = "say";

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        final String methodName = method.getName();
        return methodName.startsWith(SAY);
    }
}
