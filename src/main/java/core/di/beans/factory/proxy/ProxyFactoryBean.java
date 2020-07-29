package core.di.beans.factory.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyFactoryBean implements FactoryBean<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ProxyFactoryBean.class);

    @Override
    public Object getObject() throws Exception {
        return null;
    }
}
