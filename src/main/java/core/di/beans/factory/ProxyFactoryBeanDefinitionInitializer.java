package core.di.beans.factory;

import org.springframework.beans.BeanInstantiationException;

/**
 * @author KingCjy
 */
public class ProxyFactoryBeanDefinitionInitializer extends AbstractBeanDefinitionInitializer {

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return beanDefinition instanceof ProxyFactoryBeanDefinition;
    }

    @Override
    public Object instantiateBean(BeanDefinition definition, BeanFactory beanFactory) {
        ProxyFactoryBeanDefinition beanDefinition = (ProxyFactoryBeanDefinition) definition;
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanDefinition.getType(), beanDefinition.getPointcut(), beanDefinition.getAdvice(), beanFactory);

        try {
            return proxyFactoryBean.getObject();
        } catch (Exception e) {
            throw new BeanInstantiationException(beanDefinition.getType(), e.getMessage(), e);
        }
    }
}
