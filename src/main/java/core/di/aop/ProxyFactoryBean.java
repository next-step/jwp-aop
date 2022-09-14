package core.di.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private T target;
    private MethodInterceptor methodInterceptor;

    @Override
    public T getObject() throws Exception {
        return (T) Enhancer.create(target.getClass(), methodInterceptor);
    }

    public void setTarget(final T target) {
        this.target = target;
    }

    public void setMethodInterceptor(final MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }
}
