package core.di.beans.factory.aop.advisor;

import java.lang.reflect.Method;

public class DefaultPointcut implements Pointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return false;
    }
}
