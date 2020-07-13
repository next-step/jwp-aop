package core.di.beans.factory.initializer;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.definition.BeanDefinition;

/**
 * @author KingCjy
 */
public abstract class AbstractBeanDefinitionInitializer implements BeanInitializer {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, BeanFactory beanFactory) {
        return support(beanDefinition) ? instantiateBean(beanDefinition, beanFactory) : null;
    }

    abstract public Object instantiateBean(BeanDefinition beanDefinition, BeanFactory beanFactory);
}
