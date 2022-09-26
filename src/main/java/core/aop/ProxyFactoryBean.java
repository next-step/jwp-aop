package core.aop;

import core.di.beans.factory.BeanFactory;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final T target;
    private final AbstractAopAdvisor advisor;
    private BeanFactory beanFactory;

    public ProxyFactoryBean(T target, AbstractAopAdvisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() throws Exception {
        if (availableCGLibDynamicProxy()) {
            return (T) new CGLibAopProxy(target, advisor, beanFactory).proxy();
        }
        return (T) new JDKAopDynamicProxy(target, advisor).proxy();
    }

    private boolean availableCGLibDynamicProxy() {
        return target.getClass().getInterfaces().length == 0;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory beanFactory() {
        return beanFactory;
    }
}
