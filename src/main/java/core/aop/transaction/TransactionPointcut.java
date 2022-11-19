package core.aop.transaction;

import java.lang.reflect.Method;

import core.annotation.Transactional;
import core.di.beans.factory.proxy.Pointcut;

public class TransactionPointcut implements Pointcut {

    @Override
    public boolean matches(Class<?> targetClass, Method method) {
        return targetClass.isAnnotationPresent(Transactional.class)
            || method.isAnnotationPresent(Transactional.class);
    }
}
