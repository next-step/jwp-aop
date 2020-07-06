package core.di.beans.factory.generator;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.DefaultBeanDefinition;

import java.util.Optional;

public class ConcreteClassBeanGenerator extends AbstractBeanGenerator {

    public ConcreteClassBeanGenerator(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return beanDefinition instanceof DefaultBeanDefinition || beanDefinition == null;
    }

    @Override
    public <T> T generate(Class<T> clazz, BeanDefinition beanDefinition) {
        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getBeanClasses());

        if (!concreteClazz.isPresent()) {
            return null;
        }

        beanDefinition = beanFactory.getBeanDefinition(concreteClazz.get());
        if (beanDefinition == null) {
            return null;
        }

        if (!(beanDefinition instanceof DefaultBeanDefinition)) {
            return (T) beanFactory.getBean(concreteClazz.get());
        }
        log.debug("BeanDefinition : {}", beanDefinition);

        Object bean = inject(beanDefinition);
        beanFactory.putBean(concreteClazz.get(), bean);
        initialize(bean, concreteClazz.get());

        return (T) bean;
    }
}
