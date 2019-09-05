package core.di.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public abstract class ProxyFactoryBean<T> implements FactoryBean<T> {

    @Override
    public T getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getType());
        enhancer.setCallback(new ProxyInterceptor(matcher(), methodInvocation()));
        return (T) enhancer.create();
    }

    protected abstract MethodMatcher matcher();

    protected abstract MethodInvocation methodInvocation();

    private static class ProxyInterceptor implements MethodInterceptor {

        private MethodMatcher matcher;
        private MethodInvocation invocation;

        public ProxyInterceptor(MethodMatcher matcher, MethodInvocation methodInvocation) {
            this.matcher = matcher;
            this.invocation = methodInvocation;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (matcher.matches(method, method.getReturnType(), args)) {
                return invocation.invoke(new ProxyInvocation(obj, method, args, proxy));
            }

            return proxy.invokeSuper(obj, args);
        }
    }

}
