package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Proxy;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private T target;
    private Class<?> interfaceBean;
    private MethodInterceptor methodInterceptor;
    private InvocationHandler invocationHandler;

    @Override
    public T getObject() throws Exception {
        if (methodInterceptor != null) {
            return (T) Enhancer.create(target.getClass(), methodInterceptor);
        }

        return (T) Proxy.newProxyInstance(interfaceBean.getClassLoader(), new Class[]{interfaceBean}, invocationHandler);
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public void setInterfaceBean(Class<?> interfaceBean) {
        this.interfaceBean = interfaceBean;
    }

    public void setInvocationHandler(InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }
}
