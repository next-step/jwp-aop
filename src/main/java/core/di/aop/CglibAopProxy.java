package core.di.aop;

import core.di.aop.exception.ProxyGenerateException;
import java.util.Objects;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibAopProxy implements AopProxy {

    private final Class<?> target;
    private final MethodInterceptor methodInterceptor;

    public CglibAopProxy(final Class<?> target, final PointcutAdvisor advisor) {
        validateRequired(target, ProxyGenerateException.target());
        validateRequired(advisor, ProxyGenerateException.advisor());
        this.target = target;
        this.methodInterceptor = (obj, method, args, proxy) -> {
            if (advisor.matches(method, target)) {
                return advisor.invoke(() -> proxy.invokeSuper(obj, args));
            }
            return proxy.invokeSuper(obj, args);
        };
    }

    private void validateRequired(final Object argument, ProxyGenerateException exception) {
        if (Objects.isNull(argument)) {
            throw exception;
        }
    }

    @Override
    public Object getProxy() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }

}
