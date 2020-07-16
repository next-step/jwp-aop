package core.di.beans.factory;

import core.aop.Advisor;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TargetProxyFactoryBean<T> implements FactoryBean<T> {

    private final T target;
    private final Advisor advisor;


    public TargetProxyFactoryBean(T target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(new ProxyMethodInterceptor(this.target, this.advisor));
        return (T) enhancer.create();
    }

    private static class ProxyMethodInterceptor implements MethodInterceptor {

        private final Object target;
        private final Advisor advisor;

        public ProxyMethodInterceptor(Object target, Advisor advisor) {
            this.target = target;
            this.advisor = advisor;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {

            if (advisor.getPointcut().match(method, obj.getClass(), args)) {
                return advisor.getAdvice().intercept(() -> method.invoke(this.target, args));
            }

            return method.invoke(this.target, args);
        }
    }
}
