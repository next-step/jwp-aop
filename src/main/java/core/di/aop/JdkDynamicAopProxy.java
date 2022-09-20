package core.di.aop;

import core.di.aop.exception.ProxyGenerateException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class JdkDynamicAopProxy implements AopProxy {

    private final Object target;
    private final InvocationHandler invocationHandler;

    public JdkDynamicAopProxy(final Object target, final PointcutAdvisor advisor) {
        validateRequired(target, ProxyGenerateException.target());
        validateRequired(advisor, ProxyGenerateException.advisor());
        this.target = target;
        this.invocationHandler = (proxy, method, args) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(() -> method.invoke(target, args));
            }

            return method.invoke(target, args);
        };
    }

    private void validateRequired(final Object argument, ProxyGenerateException exception) {
        if (Objects.isNull(argument)) {
            throw exception;
        }
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
            target.getClass().getClassLoader(), target.getClass().getInterfaces(), invocationHandler
        );
    }
}
