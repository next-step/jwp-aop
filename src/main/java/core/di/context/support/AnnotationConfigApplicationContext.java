package core.di.context.support;

import com.google.common.collect.Lists;
import core.annotation.ComponentScan;
import core.di.beans.factory.BeanPostProcessor;
import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.ApplicationContext;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import java.util.Collections;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotationConfigApplicationContext implements ApplicationContext {
    private static final Logger log = LoggerFactory.getLogger(AnnotationConfigApplicationContext.class);

    private DefaultBeanFactory beanFactory;

    public AnnotationConfigApplicationContext(List<BeanPostProcessor> postProcessors, Class<?>... annotatedClasses) {
        Object[] basePackages = findBasePackages(annotatedClasses);
        beanFactory = new DefaultBeanFactory();
        postProcessors.forEach(beanPostProcessor -> beanFactory.addBeanPostProcessor(beanPostProcessor));

        BeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(annotatedClasses);

        if (basePackages.length > 0) {
            ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
            scanner.doScan(basePackages);
        }
        beanFactory.preInstantiateSingletons();
    }

    public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
        this(Collections.EMPTY_LIST, annotatedClasses);
    }

    private Object[] findBasePackages(Class<?>[] annotatedClasses) {
        List<Object> basePackages = Lists.newArrayList();
        for (Class<?> annotatedClass : annotatedClasses) {
            ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            if (componentScan == null) {
                continue;
            }
            for (String basePackage : componentScan.value()) {
                log.info("Component Scan basePackage : {}", basePackage);
            }
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
