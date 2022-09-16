package core.aop;

import java.lang.reflect.Method;

public class TrueMethodMatcher implements MethodMatcher {

    public static final TrueMethodMatcher INSTANCE = new TrueMethodMatcher();

    private TrueMethodMatcher() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return true;
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        throw new UnsupportedOperationException();
    }
}
