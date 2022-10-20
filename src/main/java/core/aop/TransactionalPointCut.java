package core.aop;

import core.annotation.Transactional;

import java.lang.reflect.Method;

public class TransactionalPointCut implements PointCut {
    private static final TransactionalPointCut INSTANCE = new TransactionalPointCut();

    public static TransactionalPointCut getInstance() {
        return INSTANCE;
    }

    private TransactionalPointCut() {
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return targetClass.isAnnotationPresent(Transactional.class) || method.isAnnotationPresent(Transactional.class);
    }
}
