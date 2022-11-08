package core.di.beans.factory.proxy.jdkdynamic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import core.di.beans.factory.proxy.Advisor;
import core.di.beans.factory.proxy.ProxyFactory;
import core.di.beans.factory.proxy.AdvisorMethodInvocation;

public class JdkDynamicProxyFactory implements ProxyFactory {

    @Override
    public <T> Object createProxy(Class<T> targetClass, T targetObject, Advisor advisor) {
        return Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(),
            new Class[] {targetClass},
            (proxy, method, args) ->
                new AdvisorMethodInvocation(
                    advisor,
                    targetClass,
                    method,
                    () -> invoke(targetObject, method, args)
                ).proceed()
        );
    }

    private <T> Object invoke(Object instance, Method method, Object[] args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}
