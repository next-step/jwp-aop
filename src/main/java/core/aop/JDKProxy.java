package core.aop;

import net.sf.cglib.proxy.InvocationHandler;

public class JDKProxy implements Proxy {

    private final Object target;
    private final InvocationHandler invocationHandler;

    public JDKProxy(Object target, Advisor advisor) {
        this.target = target;
        this.invocationHandler = (proxy, method, args) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(proxy, method, args);
            }

            return method.invoke(target, args);
        };
    }

    @Override
    public Object proxy() {
        return net.sf.cglib.proxy.Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                invocationHandler
        );
    }
}
