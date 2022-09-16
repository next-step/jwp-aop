package core.di.aop;

import java.lang.reflect.Method;

public class ImplementedPointcut implements Pointcut {

    private final Class<?> interfaceClass;

    public ImplementedPointcut(final Class<?> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        return interfaceClass.isAssignableFrom(targetClass);
    }

}
