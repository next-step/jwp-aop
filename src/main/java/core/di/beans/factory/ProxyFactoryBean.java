package core.di.beans.factory;

import core.di.beans.factory.proxy.Advisor;
import core.di.beans.factory.proxy.ProxyFactory;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> targetClass;
    private final T targetObject;
    private final Advisor advisor;
    private final ProxyFactory proxyFactory;

    public ProxyFactoryBean(
        Class<T> targetClass,
        T targetObject,
        Advisor advisor,
        ProxyFactory proxyFactory
    ) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.advisor = advisor;
        this.proxyFactory = proxyFactory;
    }

    @Override
    public T getObject() throws Exception {
        return (T)proxyFactory.createProxy(targetClass, targetObject, advisor);
    }

    @Override
    public Class<T> getClassType() {
        return targetClass;
    }
}
