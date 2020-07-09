package core.di.beans.factory.support.injector;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanGettable;

@FunctionalInterface
public interface BeanInjector {
    <T> T inject(BeanGettable beanGettable, BeanDefinition beanDefinition);
}
