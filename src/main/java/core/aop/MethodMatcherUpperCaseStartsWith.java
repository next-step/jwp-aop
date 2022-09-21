package core.aop;

import java.lang.reflect.Method;

public class MethodMatcherUpperCaseStartsWith implements MethodMatcher {

    private String nameStart;

    public MethodMatcherUpperCaseStartsWith() {
    }

    public MethodMatcherUpperCaseStartsWith(String nameStart) {
        this.nameStart = nameStart;
    }

    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        if (nameStart == null || nameStart.isEmpty()) {
            return m.getName().startsWith("say");
        }
        return m.getName().startsWith(nameStart);
    }

}
