package core.aop;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JDKDynamicProxy implements AopProxy {

    private final Object target;
    private final Class<?> proxyInterface;
    private final InvocationHandler invocationHandler;

    public JDKDynamicProxy(Object target, Class<?> proxyInterface, Advisor advisor) {
        this.target = target;
        this.proxyInterface = proxyInterface;
        this.invocationHandler = (proxy, method, args) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(target, method, args);
            }
            return method.invoke(target, args);
        };
    }

    @Override
    public Object proxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[] {proxyInterface}, invocationHandler);
    }
}
