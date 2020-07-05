package core.di.context.annotation;

import com.google.common.collect.Sets;
import core.annotation.Component;
import core.annotation.Repository;
import core.annotation.Service;
import core.annotation.web.Controller;
import core.di.beans.factory.config.BeanDefinitionConverters;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClasspathBeanDefinitionScanner {
    private final BeanDefinitionRegistry beanDefinitionRegistry;
    private final BeanDefinitionConverters converters;

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this(beanDefinitionRegistry, new BeanDefinitionConverters());
    }

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry,
                                          BeanDefinitionConverters converters) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.converters = converters;
    }

    @SuppressWarnings("unchecked")
    public void doScan(Object... basePackages) {
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class,
                Repository.class, Component.class);

        beanClasses.stream()
                .map(converters::convert)
                .forEach(beanDefinition ->
                        beanDefinitionRegistry.registerBeanDefinition(beanDefinition.getBeanClass(), beanDefinition)
                );
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiatedBeans;
    }
}
