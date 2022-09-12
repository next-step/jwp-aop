package core.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {

    private final Object proxyTarget;

    public HelloMethodInterceptor(Object proxyTarget) {
        this.proxyTarget = proxyTarget;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        Object invoke = proxy.invoke(proxyTarget, objects);

        if (method.getReturnType().equals(String.class)) {
            return invoke.toString().toUpperCase();
        }

        return invoke;
    }
}
