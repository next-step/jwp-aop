package core.di.beans.factory;

import core.aop.Advisor;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> targetClass;
    private final Object[] args;
    private final Advisor advisor;

    public ProxyFactoryBean(Class<T> targetClass, Object[] args) {
        this(targetClass, args, Advisor.NOTHING);
    }

    public ProxyFactoryBean(Class<T> targetClass, Object[] args, Advisor advisor) {
        this.targetClass = targetClass;
        this.args = args;
        this.advisor = advisor;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.targetClass);
        enhancer.setCallback(new ProxyMethodInterceptor(this.advisor));

        Class[] argumentTypes = Stream.of(this.args).map(o -> o.getClass()).toArray(value -> new Class[this.args.length]);
        return (T) enhancer.create(argumentTypes, args);
    }

    private static class ProxyMethodInterceptor implements MethodInterceptor {

        private final Advisor advisor;

        private ProxyMethodInterceptor(Advisor advisor) {
            this.advisor = advisor;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {

            if (advisor.getPointcut().match(method, obj.getClass(), args)) {
                return advisor.getAdvice().intercept(() -> proxy.invokeSuper(obj, args));
            }

            return proxy.invokeSuper(obj, args);
        }
    }
}
