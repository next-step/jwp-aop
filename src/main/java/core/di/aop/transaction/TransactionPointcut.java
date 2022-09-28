package core.di.aop.transaction;

import core.annotation.Transactional;
import core.di.aop.Pointcut;
import java.lang.reflect.Method;

public class TransactionPointcut implements Pointcut {

    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        return method.isAnnotationPresent(Transactional.class) || targetClass.isAnnotationPresent(Transactional.class);
    }
}
