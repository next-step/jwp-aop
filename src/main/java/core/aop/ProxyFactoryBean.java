package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Created by iltaek on 2020/07/21 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ProxyFactoryBean<T, K extends MethodInterceptor> implements FactoryBean<T> {

    private final Enhancer enhancer = new Enhancer();
    private final T target;
    private K advice;

    public ProxyFactoryBean(T target) {
        this.target = target;
        enhancer.setSuperclass(target.getClass());
    }

    public void addAdvice(K advice) {
        this.advice = advice;
    }

    @Override
    public T getObject() throws Exception {
        enhancer.setCallback(advice);
        return (T) enhancer.create();
    }

    @Override
    public Class<? extends T> getObjectType() {
        return (Class<? extends T>) target.getClass();
    }
}
