package core.di.beans.factory;

import core.di.beans.factory.config.BeanDefinition;

public interface BeanGenerator {
    boolean support(BeanDefinition beanDefinition);
    <T> T generate(Class<T> clazz, BeanDefinition beanDefinition);
}
