package core.di.beans.factory.processor;

import core.aop.FactoryBean;
import core.di.beans.factory.definition.BeanDefinition;
import core.di.beans.factory.definition.FactoryBeanDefinition;

/**
 * @author KingCjy
 */
public class FactoryBeanDefinitionPostProcessor implements BeanDefinitionPostProcessor {
    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return FactoryBean.class.isAssignableFrom(beanDefinition.getType());
    }

    @Override
    public BeanDefinition process(BeanDefinition beanDefinition) {
        return new FactoryBeanDefinition(beanDefinition);
    }
}
