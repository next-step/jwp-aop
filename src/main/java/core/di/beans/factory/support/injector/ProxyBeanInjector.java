package core.di.beans.factory.support.injector;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.BeanGettable;

import java.lang.reflect.Method;

public class ProxyBeanInjector implements BeanInjector {
    @Override
    public <T> T inject(BeanGettable beanGettable, BeanDefinition beanDefinition) {
        Method method = beanDefinition.getMethod();
        return (T) BeanFactoryUtils.invokeMethod(
            method,
            beanGettable.getBean(method.getDeclaringClass()),
            BeanFactoryUtils.getArguments(beanGettable, method.getParameterTypes())
        );
    }
}
