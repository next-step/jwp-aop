package core.di.beans.factory.support;

import core.di.beans.factory.ProxyFactoryBean;
import org.springframework.beans.BeanUtils;

public class ProxyBeanDefinition extends DefaultBeanDefinition {

    private final ProxyFactoryBean proxyFactoryBean;

    public ProxyBeanDefinition(ProxyFactoryBean proxyFactoryBean) {
        super(proxyFactoryBean.getObjectType());
        this.proxyFactoryBean = proxyFactoryBean;
    }

    public ProxyBeanDefinition(Class<?> targetClass) {
        super(targetClass);
        this.proxyFactoryBean = new ProxyFactoryBean();
        final Object targetInstance = BeanUtils.instantiateClass(targetClass);
        proxyFactoryBean.setTarget(targetInstance);
    }

    public ProxyFactoryBean getProxyFactoryBean() {
        return proxyFactoryBean;
    }
}
