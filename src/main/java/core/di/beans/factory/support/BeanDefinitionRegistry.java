package core.di.beans.factory.support;

import core.di.beans.factory.config.BeanDefinition;

import java.util.Map;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition);

    Map<Class<?>, BeanDefinition> getBeanDefinitions();
}
