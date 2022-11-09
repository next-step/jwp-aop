package core.transaction;

import core.annotation.Transactional;
import core.aop.Pointcut;

import java.lang.reflect.Method;

public class TransactionPointCut implements Pointcut {

    private TransactionPointCut() {}

    public static TransactionPointCut getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public boolean matches(Class<?> targetClass, Method method) {
        return targetClass.isAnnotationPresent(Transactional.class) || method.isAnnotationPresent(Transactional.class);
    }

    private static class Holder {
        private static final TransactionPointCut INSTANCE = new TransactionPointCut();
    }
}
