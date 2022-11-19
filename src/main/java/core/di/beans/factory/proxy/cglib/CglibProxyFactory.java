package core.di.beans.factory.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import core.di.beans.factory.proxy.Advisor;
import core.di.beans.factory.proxy.AdvisorMethodInvocation;
import core.di.beans.factory.proxy.JoinPoint;
import core.di.beans.factory.proxy.ProxyFactory;

public class CglibProxyFactory implements ProxyFactory {

    private final Enhancer enhancer = new Enhancer();

    public CglibProxyFactory() {
    }

    @Override
    public <T> Object createProxy(Class<T> targetClass, T targetObject, Advisor advisor) {
        enhancer.setSuperclass(targetClass);

        var methodInterceptor = (MethodInterceptor)(obj, method, args, proxy) -> {
            JoinPoint joinPoint = () -> invoke(targetObject, args, proxy);
            return new AdvisorMethodInvocation(advisor, targetClass, method, joinPoint).proceed();
        };
        enhancer.setCallbacks(new Callback[] {methodInterceptor});

        return enhancer.create();
    }

    private Object invoke(Object obj, Object[] objects, MethodProxy proxy) {
        try {
            return proxy.invoke(obj, objects);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }
}
