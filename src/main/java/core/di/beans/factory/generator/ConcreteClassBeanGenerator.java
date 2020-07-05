package core.di.beans.factory.generator;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanFactoryUtils;

import java.util.Optional;

public class ConcreteClassBeanGenerator extends AbstractBeanGenerator {

    public ConcreteClassBeanGenerator(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return true;
    }

    @Override
    public <T> T generate(Class<T> clazz, BeanDefinition beanDefinition) {
        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getBeanClasses());

        if (!concreteClazz.isPresent()) {
            return null;
        }

        beanDefinition = beanFactory.getBeanDefinition(concreteClazz.get());
        log.debug("BeanDefinition : {}", beanDefinition);

        if (beanDefinition == null) {
            return null;
        }

        return (T) inject(beanDefinition);
    }
}
