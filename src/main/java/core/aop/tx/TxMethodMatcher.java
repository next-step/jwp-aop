package core.aop.tx;

import core.annotation.Transactional;
import core.aop.pointcut.MethodMatcher;

import java.lang.reflect.Method;

public class TxMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        return method.isAnnotationPresent(Transactional.class);
    }

}
