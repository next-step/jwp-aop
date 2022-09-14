package core.di.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private T target;
    private MethodInterceptor methodInterceptor;
    private Class<?> superInterface;
    private InvocationHandler invocationHandler;


    @Override
    public T getObject() throws Exception {
        if (methodInterceptor != null) {
            return (T) Enhancer.create(target.getClass(), methodInterceptor);
        }

        return (T) Proxy.newProxyInstance(
            superInterface.getClassLoader(), new Class[]{superInterface}, invocationHandler
        );

    }

    public void setTarget(final T target) {
        this.target = target;
    }

    public void setMethodInterceptor(final MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public void setSuperInterface(final Class<?> superInterface) {
        this.superInterface = superInterface;
    }

    public void setInvocationHandler(final InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }
}
