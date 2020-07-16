package core.aop.transactional;

import core.annotation.Transactional;
import core.aop.pointcut.Pointcut;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class TransactionalPointcut implements Pointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        if (Objects.isNull(method)) {
            return false;
        }

        return method.getDeclaringClass().isAnnotationPresent(Transactional.class) || method.isAnnotationPresent(Transactional.class);
    }
}
