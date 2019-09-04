package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.support.MethodInvocation;
import core.di.beans.factory.support.MethodMatcher;
import core.di.beans.factory.support.ProxyFactoryBean;

@Component
public class ProxyInValidFactoryBeanService extends ProxyFactoryBean<FactoryNotTestService> {

    public static boolean isBeforeExecuted = false;
    public static boolean isAfterExecuted = false;

    @Override
    protected MethodMatcher matcher() {
        return (m, returnType, args) -> m.getName().startsWith("not");
    }

    @Override
    protected MethodInvocation methodInvocation() {
        return proxyInvocation -> {
            isBeforeExecuted = true;
            Object result = proxyInvocation.proceed();
            isAfterExecuted = true;
            return result;
        };
    }

    @Override
    public Class<?> getType() {
        return FactoryNotTestService.class;
    }
}
