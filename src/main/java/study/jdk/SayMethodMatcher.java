package study.jdk;

import java.lang.reflect.Method;

/**
 * Created by youngjae.havi on 2019-09-01
 */
public class SayMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method m, Class targetClass, Object[] args) {
        return m.getName().startsWith("say");
    }
}
