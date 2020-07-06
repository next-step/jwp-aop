package core.di.beans.factory.support;

import core.di.beans.factory.ProxyFactoryBean;
import org.springframework.beans.BeanUtils;

public class ProxyBeanDefinition extends DefaultBeanDefinition {

    private final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

    public ProxyBeanDefinition(Class<?> clazz) {
        super(clazz);
        final Object target = BeanUtils.instantiateClass(clazz);
        proxyFactoryBean.setTarget(target);
    }

    private ProxyFactoryBean getProxyFactoryBean() {
        return proxyFactoryBean;
    }
}
