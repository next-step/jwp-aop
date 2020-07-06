package core.di.beans.factory.generator;

import core.aop.ConcreteProxyBeanDefinition;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConcreteProxyBeanGenerator extends AbstractBeanGenerator {

    public ConcreteProxyBeanGenerator(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return beanDefinition instanceof ConcreteProxyBeanDefinition;
    }

    @Override
    public <T> T generate(Class<T> clazz, BeanDefinition beanDefinition) {
        FactoryBean proxyBean = (FactoryBean) createConcreteProxyBean(beanDefinition);
        Object targetBean = proxyBean.getObject();

        initialize(targetBean, clazz);
        beanFactory.putBean(clazz, targetBean);

        return (T) targetBean;
    }

    private Object createConcreteProxyBean(BeanDefinition beanDefinition) {
        ConcreteProxyBeanDefinition proxyBeanDefinition = (ConcreteProxyBeanDefinition) beanDefinition;
        Constructor<?> constructor = proxyBeanDefinition.getInjectConstructor();
        Object[] args = populateArguments(proxyBeanDefinition.getTargetConstructorParameterTypes());

        try {
            constructor.setAccessible(true);
            return constructor.newInstance(proxyBeanDefinition.getTargetBeanDefinition(), args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("Fail to make proxy bean {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
