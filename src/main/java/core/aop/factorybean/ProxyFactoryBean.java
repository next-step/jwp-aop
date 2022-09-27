package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Proxy;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private T target;
    private Class<?> superClass;
    private MethodInterceptor methodInterceptor;
    private InvocationHandler invocationHandler;

    @Override
    public T getObject() {
        if (methodInterceptor != null) {
            return (T) Enhancer.create(target.getClass(), methodInterceptor);
        }

        return (T) Proxy.newProxyInstance(superClass.getClassLoader(),
                new Class[]{superClass},
                new DynamicInvocationHandler(target, new PrefixSayMatcher()));

    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public void setSuperClass(Class<T> superClass) {
        this.superClass = superClass;
    }

    public void setInvocationHandler(InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }
}
