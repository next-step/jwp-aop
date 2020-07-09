package core.di.beans.factory.support.injector;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanGettable;
import org.springframework.beans.BeanUtils;

public class DefaultBeanInjector implements BeanInjector {
    @Override
    public <T> T inject(BeanGettable beanGettable, BeanDefinition beanDefinition) {
        return (T) BeanUtils.instantiateClass(beanDefinition.getBeanClass());
    }
}
