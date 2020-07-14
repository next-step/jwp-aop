package core.di.beans.factory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    private T target;
    private MethodInterceptor methodInterceptor;

    public ProxyFactoryBean(T target, MethodInterceptor interceptor) {
        this.target = target;
        this.methodInterceptor = interceptor;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this.methodInterceptor);

        return (T) enhancer.create();
    }
}
