package core.di.context;

import core.di.beans.factory.config.BeanDefinition;

import java.util.List;
import java.util.Set;

public interface ApplicationContext {
    <T> T getBean(Class<T> clazz);

    Set<Class<?>> getBeanClasses();

    List<BeanDefinition> getBeanDefinitions();

}