package core.di.beans.factory.processor;

import core.di.beans.factory.definition.BeanDefinition;

/**
 * @author KingCjy
 */
public interface BeanPostProcessor {
    Object postProcess(BeanDefinition beanDefinition, Object bean) throws Exception;
}
