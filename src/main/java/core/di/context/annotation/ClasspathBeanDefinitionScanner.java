package core.di.context.annotation;

import core.annotation.Component;
import core.annotation.Repository;
import core.annotation.Service;
import core.annotation.web.Controller;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import core.di.beans.factory.support.DefaultBeanDefinition;
import org.reflections.Reflections;

import java.util.Set;

public final class ClasspathBeanDefinitionScanner extends AbstractBeanDefinitionScanner {

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doScan(Object... basePackages) {
        final Reflections reflections = new Reflections(basePackages);
        final Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class,
                Repository.class, Component.class);
        for (Class<?> clazz : beanClasses) {
            beanDefinitionRegistry.registerBeanDefinition(clazz, new DefaultBeanDefinition(clazz));
        }
    }

}
