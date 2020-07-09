package core.di.context.annotation;

import com.google.common.collect.Sets;
import core.annotation.Component;
import core.aop.ProxyFactoryBean;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.config.BeanDefinitionConverters;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import core.mvc.JsonUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
        Reflections reflections = new Reflections(
                basePackages,
                new SubTypesScanner(false),
                new TypeAnnotationsScanner()
        );
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Component.class);

        beanClasses.stream()
                .map(converters::convert)
                .forEach(beanDefinition ->
                        beanDefinitionRegistry.registerBeanDefinition(beanDefinition.getBeanClass(), beanDefinition)
                );
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
        Set<Class<?>> subTypes = reflections.getSubTypesOf(Object.class);
        subTypes.addAll(reflections.getSubTypesOf(ProxyFactoryBean.class));

        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiatedBeans.addAll(getTypesAnnotatedWith(subTypes, annotation));
        }
        return preInstantiatedBeans;
    }

    private Set<Class<?>> getTypesAnnotatedWith(Set<Class<?>> classes, Class<? extends Annotation> annotation) {
        return classes.stream()
                .filter(clazz ->
                        clazz.isAnnotationPresent(annotation) ||
                                Arrays.stream(clazz.getAnnotations())
                                        .anyMatch(a -> a.annotationType().isAnnotationPresent(annotation))
                ).collect(Collectors.toSet());
    }
}
