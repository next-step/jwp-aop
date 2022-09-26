package core.aop;

import core.annotation.Transactional;

import java.lang.reflect.Method;

public class TransactionPointCut implements Pointcut {
    @Override
    public boolean matches(Method method, Class<?> target) {
        return method.isAnnotationPresent(Transactional.class) ||
                target.isAnnotationPresent(Transactional.class);
    }
}
