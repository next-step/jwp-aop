package core.aop;

import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private final Enhancer enhancer;

    public ProxyFactoryBean(Class<?> clazz, BeanInterceptor beanInterceptor) {
        enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(beanInterceptor);
    }

    @Override
    public Object getObject() throws Exception {
        return enhancer.create();
    }
}
