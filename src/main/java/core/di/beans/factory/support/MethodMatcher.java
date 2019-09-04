package core.di.beans.factory.support;

import java.lang.reflect.Method;

public interface MethodMatcher {

    boolean matches(Method m, Class returnType, Object[] args);

}
