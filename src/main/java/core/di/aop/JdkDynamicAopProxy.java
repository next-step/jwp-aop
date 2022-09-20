package core.di.aop;

import core.di.aop.exception.ProxyGenerateException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class JdkDynamicAopProxy implements AopProxy {

    private final Class<?> superInterface;
    private final InvocationHandler invocationHandler;

    public JdkDynamicAopProxy(final Object target, final Class<?> superInterface, final PointcutAdvisor advisor) {
        validateRequired(target, ProxyGenerateException.target());
        validateRequired(superInterface, ProxyGenerateException.superInterface());
        validateRequired(advisor, ProxyGenerateException.advisor());
        this.superInterface = superInterface;
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
            superInterface.getClassLoader(), new Class[]{superInterface}, invocationHandler
        );
    }
}
