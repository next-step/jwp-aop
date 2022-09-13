package study.aop;

import java.lang.reflect.Method;

public class MethodMatcherUpperCaseStartsWith implements MethodMatcher {

    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        return m.getName().startsWith("say");
    }

}
