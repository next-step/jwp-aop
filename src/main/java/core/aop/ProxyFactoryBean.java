package core.aop;

import core.di.beans.factory.FactoryBean;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private T target;
    private List<Advisor> advisors;

    public ProxyFactoryBean(T target, List<Advisor> advisors) {
        this.target = target;
        this.advisors = advisors;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(new ProxyMethodInterceptor(this.target, this.advisors));

        Class[] types = this.target.getClass().getDeclaredConstructors()[0].getParameterTypes();
        return (T) enhancer.create(types, new Object[types.length]);
    }
}
