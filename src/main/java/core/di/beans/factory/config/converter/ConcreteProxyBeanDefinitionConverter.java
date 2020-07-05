package core.di.beans.factory.config.converter;

import core.aop.ProxyBeanDefinition;
import core.aop.ProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.config.BeanDefinitionConverter;

public class ConcreteProxyBeanDefinitionConverter implements BeanDefinitionConverter {

    @Override
    public boolean support(Class<?> clazz) {
        return ProxyFactoryBean.class
                .isAssignableFrom(clazz);
    }

    @Override
    public BeanDefinition convert(Class<?> clazz) {
        return new ProxyBeanDefinition(clazz);
    }
}
