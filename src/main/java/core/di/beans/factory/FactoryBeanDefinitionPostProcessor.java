package core.di.beans.factory;

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
