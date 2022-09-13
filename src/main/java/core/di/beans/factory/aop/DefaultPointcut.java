package core.di.beans.factory.aop;

import java.lang.reflect.Method;

public class DefaultPointcut implements Pointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return true;
    }
}
