package core.aop;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JDKAopDynamicProxy implements AopProxy {

    private final Object target;
    private final InvocationHandler invocationHandler;

    public JDKAopDynamicProxy(Object target, Advisor advisor) {
        this.target = target;
        this.invocationHandler = (proxy, method, args) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(target, method, args);
            }
            return method.invoke(target, args);
        };
    }

    @Override
    public Object proxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), invocationHandler);
    }
}
