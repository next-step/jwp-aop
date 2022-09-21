package core.di.beans.factory.aop.advisor;

import core.annotation.Transactional;

import java.lang.reflect.Method;

public class TransactionalPointcut implements Pointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.isAnnotationPresent(Transactional.class) ||
                method.getDeclaringClass().isAnnotationPresent(Transactional.class);
    }
}
