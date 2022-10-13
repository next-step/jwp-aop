package core.di.beans.factory.support;

import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Callback;
import next.wrapper.EnhancerWrapper;

public class DefaultFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> clazz;
    private final EnhancerWrapper enhancerWrapper;

    public DefaultFactoryBean(Class<T> clazz, Callback callback) {
        this.clazz = clazz;
        enhancerWrapper = new EnhancerWrapper(clazz, callback);
    }

    @Override
    public T getObject() throws Exception {
        return enhancerWrapper.create();
    }

    @Override
    public Class<T> getObjectType() {
        return clazz;
    }
}