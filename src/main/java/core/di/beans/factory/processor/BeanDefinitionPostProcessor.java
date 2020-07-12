package core.di.beans.factory.processor;

import core.di.beans.factory.definition.BeanDefinition;

/**
 * @author KingCjy
 */
public interface BeanDefinitionPostProcessor {
    boolean support(BeanDefinition beanDefinition);
    BeanDefinition process(BeanDefinition beanDefinition);
}
