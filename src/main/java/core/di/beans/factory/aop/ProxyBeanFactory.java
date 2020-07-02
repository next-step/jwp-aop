package core.di.beans.factory.aop;

import core.di.beans.factory.config.BeanDefinition;

public interface ProxyBeanFactory<T> {
    T getType();
    boolean support(BeanDefinition beanDefinition);
    Object getBean(BeanDefinition beanDefinition);
}
