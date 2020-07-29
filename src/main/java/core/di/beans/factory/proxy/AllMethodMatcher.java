package core.di.beans.factory.proxy;

import java.lang.reflect.Method;

public class AllMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method m, Class targetClass) {
        return true;
    }
}
