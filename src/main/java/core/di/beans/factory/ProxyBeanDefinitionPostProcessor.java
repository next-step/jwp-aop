package core.di.beans.factory;

/**
 * @author KingCjy
 */
public class ProxyBeanDefinitionPostProcessor implements BeanDefinitionPostProcessor {
    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return ProxyFactoryBean.class.isAssignableFrom(beanDefinition.getType());
    }

    @Override
    public BeanDefinition process(BeanDefinition beanDefinition) {
//        return new ProxyFactoryBeanDefinition(beanDefinition.getName(), beanDefinition.getType(), );
        return null;
    }

}
