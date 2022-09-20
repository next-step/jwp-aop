package core.aop.support;

import java.lang.reflect.Method;

import core.annotation.Transactional;

public class TransactionPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.isAnnotationPresent(Transactional.class)
            || targetClass.isAnnotationPresent(Transactional.class);
    }
}
