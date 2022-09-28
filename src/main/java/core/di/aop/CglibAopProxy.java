package core.di.aop;

import core.di.aop.exception.ProxyGenerateException;
import core.di.beans.factory.support.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibAopProxy implements AopProxy {

    private final Class<?> target;
    private final PointcutAdvisor advisor;

    public CglibAopProxy(final Class<?> target, final PointcutAdvisor advisor) {
        validateRequired(target, ProxyGenerateException.target());
        validateRequired(advisor, ProxyGenerateException.advisor());
        this.target = target;
        this.advisor = advisor;
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
        enhancer.setCallback(getMethodInterceptor());

        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(target);
        if (constructor != null) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] arguments = getArguments(parameterTypes);
            return enhancer.create(parameterTypes, arguments);
        }

        return enhancer.create();
    }

    private MethodInterceptor getMethodInterceptor() {
        return (obj, method, args, proxy) -> {
            if (advisor.matches(method, target)) {
                return advisor.invoke(() -> proxy.invokeSuper(obj, args));
            }
            return proxy.invokeSuper(obj, args);
        };
    }

    private Object[] getArguments(final Class<?>[] parameterTypes) {
        if (ProxyPointcutAdvisor.class.isAssignableFrom(advisor.getClass())) {
            final ProxyPointcutAdvisor proxyPointcutAdvisor = (ProxyPointcutAdvisor) advisor;
            return Arrays.stream(parameterTypes)
                .map(proxyPointcutAdvisor::getBean)
                .toArray();
        }

        return new Object[0];
    }

}
