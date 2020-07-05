package core.di.beans.factory;

import core.di.beans.factory.config.BeanDefinition;

import java.util.Set;

public interface BeanFactory {
    Set<Class<?>> getBeanClasses();

    BeanDefinition getBeanDefinition(Class<?> clazz);

    <T> T getBean(Class<T> clazz);

    void putBean(Class<?> clazz, Object bean);

    void clear();
}