package study;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        if(m.getName().startsWith("say")) {
            return true;
        }
        return false;
    }
}