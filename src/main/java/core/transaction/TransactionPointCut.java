package core.transaction;

import core.annotation.Transactional;
import core.aop.Pointcut;

import java.lang.reflect.Method;

public class TransactionPointCut implements Pointcut {

    private TransactionPointCut() {
    }

    public static TransactionPointCut instance() {
        return TransactionPointCutHolder.INSTANCE;
    }

    @Override
    public boolean matches(Class<?> targetClass) {
        return targetClass.isAnnotationPresent(Transactional.class);
    }

    @Override
    public boolean matches(Method method) {
        return method.isAnnotationPresent(Transactional.class);
    }

    private static class TransactionPointCutHolder {
        private static final TransactionPointCut INSTANCE = new TransactionPointCut();
    }
}
