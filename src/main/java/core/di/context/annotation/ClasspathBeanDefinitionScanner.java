package core.di.context.annotation;

import com.google.common.collect.Sets;
import core.annotation.Component;
import core.annotation.Repository;
import core.annotation.Service;
import core.annotation.web.Controller;
import core.aop.ProxyBeanDefinition;
import core.aop.ProxyFactoryBean;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import core.di.beans.factory.support.DefaultBeanDefinition;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClasspathBeanDefinitionScanner {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @SuppressWarnings("unchecked")
    public void doScan(Object... basePackages) {
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class,
                Repository.class, Component.class);
        for (Class<?> clazz : beanClasses) {
            if (ProxyFactoryBean.class.isAssignableFrom(clazz)) {
                System.out.println("ProxyBean register : " + clazz);
                ProxyBeanDefinition proxyBeanDefinition = new ProxyBeanDefinition(clazz);
                beanDefinitionRegistry.registerBeanDefinition(proxyBeanDefinition.getTargetClass(), proxyBeanDefinition);
            } else {
                beanDefinitionRegistry.registerBeanDefinition(clazz, new DefaultBeanDefinition(clazz));
            }
        }
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
