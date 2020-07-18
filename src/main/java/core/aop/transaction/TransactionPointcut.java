package core.aop.transaction;

import core.annotation.Transactional;
import core.aop.Pointcut;
import java.lang.reflect.Method;

class TransactionPointcut implements Pointcut {

    @Override
    public boolean match(Method method, Class targetClass, Object[] args) {
        return method.isAnnotationPresent(Transactional.class);
    }
}
