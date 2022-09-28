package core.di.aop;

import java.lang.reflect.Method;

public interface Advisor {

    Object invoke(MethodInvocation methodInvocation) throws Throwable;

    boolean matches(Method method, Class<?> targetClass);
}
