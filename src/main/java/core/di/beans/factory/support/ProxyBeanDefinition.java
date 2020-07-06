package core.di.beans.factory.support;

import core.di.beans.factory.ProxyFactoryBean;
import org.springframework.beans.BeanUtils;

public class ProxyBeanDefinition extends DefaultBeanDefinition {

    private final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

    public ProxyBeanDefinition(Class<?> targetClass) {
        super(targetClass);
        final Object targetInstance = BeanUtils.instantiateClass(targetClass);
        proxyFactoryBean.setTarget(targetInstance);
    }

    public ProxyFactoryBean getProxyFactoryBean() {
        return proxyFactoryBean;
    }
}
