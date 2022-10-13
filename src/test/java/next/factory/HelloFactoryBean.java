package next.factory;

import core.di.beans.factory.FactoryBean;
import net.sf.cglib.proxy.Callback;
import next.hello.HelloTarget;
import next.wrapper.EnhancerWrapper;

public class HelloFactoryBean implements FactoryBean<HelloTarget> {

    private final Class<HelloTarget> clazz;
    private final EnhancerWrapper enhancerWrapper;

    public HelloFactoryBean(Class<HelloTarget> clazz, Callback callback) {
        this.clazz = clazz;
        enhancerWrapper = new EnhancerWrapper(clazz, callback);
    }

    @Override
    public HelloTarget getObject() throws Exception {
        return enhancerWrapper.create();
    }
}