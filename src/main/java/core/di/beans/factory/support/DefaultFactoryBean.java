package core.di.beans.factory.support;

import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.aop.Aspect;
import core.di.beans.factory.aop.Target;
import next.wrapper.EnhancerWrapper;

public class DefaultFactoryBean<T> implements FactoryBean<T> {

    private final Target target;
    private final Aspect aspect;
    private final EnhancerWrapper enhancerWrapper;

    public DefaultFactoryBean(Target target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
        enhancerWrapper = new EnhancerWrapper(target.getClazz(), aspect);
    }

    @Override
    public T getObject() throws Exception {
        return enhancerWrapper.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClazz();
    }
}