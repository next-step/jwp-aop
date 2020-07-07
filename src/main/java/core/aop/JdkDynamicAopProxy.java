package core.aop;

import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy<T> implements AopProxy<T>, InvocationHandler {
    private ProxyFactoryBean proxyFactoryBean;

    public JdkDynamicAopProxy(ProxyFactoryBean proxyFactoryBean) {
        this.proxyFactoryBean = proxyFactoryBean;
    }

    @Override
    public T getProxy() {
        return (T) Proxy.newProxyInstance(
            ClassUtils.getDefaultClassLoader(),
            (Class<?>[]) proxyFactoryBean.getInterfaces().toArray(new Class<?>[0]),
            this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object retVal;

        if (CollectionUtils.isEmpty(proxyFactoryBean.getAdvisors())) {
            retVal = AopUtils.invokeJoinpointUsingReflection(proxyFactoryBean.getTarget(), method, args);
        }
        else {
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(
                proxy,
                proxyFactoryBean.getTarget().getClass(),
                proxyFactoryBean.getTarget(),
                method,
                args,
                proxyFactoryBean.getAdvisors()
            );

            retVal = invocation.proceed();
        }

        return retVal;
    }

}
