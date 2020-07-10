package core.di.beans.factory;

import core.annotation.*;
import core.annotation.web.Controller;
import core.util.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassBeanScanner implements BeanScanner {

    private static final Class<? extends Annotation>[] SCAN_ANNOTATIONS = new Class[] { Controller.class, Service.class, Repository.class, Component.class, Configuration.class};

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public ClassBeanScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void scan(Object... basePackages) {
        scanPackages(new LinkedHashSet<>(), basePackages);
    }

    public void scanPackages(Set<Class<?>> scannedClasses, Object... basePackages) {

        Reflections reflections = new Reflections(basePackages, new TypeAnnotationsScanner(), new SubTypesScanner(false), new MethodAnnotationsScanner());
        Set<Class<?>> getAnnotatedClasses = ReflectionUtils.getAnnotatedClasses(reflections, SCAN_ANNOTATIONS);

        for (Class<?> targetClass : getAnnotatedClasses) {
            if(scannedClasses.contains(targetClass) || targetClass.isAnnotation()) {
                continue;
            }

            String name = ReflectionUtils.getComponentName(targetClass);
            ClassBeanDefinition beanDefinition = new ClassBeanDefinition(targetClass, name);
            scannedClasses.add(targetClass);
            beanDefinitionRegistry.registerDefinition(beanDefinition);

            if(targetClass.isAnnotationPresent(ComponentScan.class)) {
                String[] scanPackages = getComponentScanPackage(targetClass);
                scanPackages(scannedClasses, scanPackages);
            }
        }
    }

    private String[] getComponentScanPackage(Class<?> targetClass) {
        if(!(targetClass.isAnnotationPresent(Configuration.class) && targetClass.isAnnotationPresent(ComponentScan.class)) ) {
            return new String[]{};
        }

        ComponentScan componentScan = targetClass.getAnnotation(ComponentScan.class);

        return componentScan.value();
    }
}
