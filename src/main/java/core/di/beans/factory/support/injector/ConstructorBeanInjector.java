package core.di.beans.factory.support.injector;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.BeanGettable;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;

public class ConstructorBeanInjector implements BeanInjector {
    @Override
    public <T> T inject(BeanGettable beanGettable, BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        Object[] args = BeanFactoryUtils.getArguments(beanGettable, constructor.getParameterTypes());
        return (T) BeanUtils.instantiateClass(constructor, args);
    }
}
