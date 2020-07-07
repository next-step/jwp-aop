package core.di.beans.factory.config.converter;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.config.BeanDefinitionConverter;
import core.di.beans.factory.support.DefaultBeanDefinition;

public class DefaultBeanDefinitionConverter implements BeanDefinitionConverter {

    @Override
    public boolean support(Class<?> clazz) {
        return true;
    }

    @Override
    public BeanDefinition convert(Class<?> clazz) {
        return new DefaultBeanDefinition(clazz);
    }
}
