package core.aop.support;

import java.lang.reflect.Method;

import core.annotation.Transactional;
import core.aop.ClassFilter;
import core.aop.MethodMatcher;
import core.aop.Pointcut;

public class TransactionPointcut implements Pointcut {

    @Override
    public ClassFilter getClassFilter() {
        return clazz -> clazz.isAnnotationPresent(Transactional.class);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return new TransactionMethodMatcher();
    }

    private static class TransactionMethodMatcher implements MethodMatcher {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.isAnnotationPresent(Transactional.class)
                || targetClass.isAnnotationPresent(Transactional.class);
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

}
