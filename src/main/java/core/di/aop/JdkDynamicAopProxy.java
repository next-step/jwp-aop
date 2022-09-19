package core.di.aop;

import core.di.aop.exception.ProxyGenerateException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class JdkDynamicAopProxy implements AopProxy {

    private final Class<?> superInterface;
    private final InvocationHandler invocationHandler;

    public JdkDynamicAopProxy(final Object target, final Class<?> superInterface, final PointcutAdvisor advisor) {
        validateRequiredField(target, ProxyGenerateException.target());
        validateRequiredField(superInterface, ProxyGenerateException.superInterface());
        validateRequiredField(advisor, ProxyGenerateException.advisor());
        this.superInterface = superInterface;
        this.invocationHandler = (proxy, method, args) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(() -> method.invoke(target, args));
            }

            return method.invoke(target, args);
        };
    }

    private void validateRequiredField(final Object requiredArgument, ProxyGenerateException proxyGenerateException) {
        if (Objects.isNull(requiredArgument)) {
            throw proxyGenerateException;
        }
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
            superInterface.getClassLoader(), new Class[]{superInterface}, invocationHandler
        );
    }
}
