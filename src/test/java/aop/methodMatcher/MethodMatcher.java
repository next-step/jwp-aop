package aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

    boolean matches(Method m, Class targetClass, Object[] args);

}
