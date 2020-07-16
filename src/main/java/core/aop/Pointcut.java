package core.aop;

import java.lang.reflect.Method;

public interface Pointcut {

    Pointcut MATCH = (method, targetClass, args) -> true;
    Pointcut NONE_MATCH = (method, targetClass, args) -> false;

    boolean match(Method method, Class targetClass, Object[] args);
}
