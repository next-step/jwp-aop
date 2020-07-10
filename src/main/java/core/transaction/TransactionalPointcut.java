package core.transaction;

import core.annotation.Transactional;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class TransactionalPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.isAnnotationPresent(Transactional.class);
    }
}
