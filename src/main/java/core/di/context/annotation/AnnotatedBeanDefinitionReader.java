package core.di.context.annotation;

import core.annotation.Bean;
import core.di.aop.FactoryBean;
import core.di.beans.factory.support.BeanDefinitionReader;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.DefaultBeanDefinition;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotatedBeanDefinitionReader implements BeanDefinitionReader {
    private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanDefinitionReader.class);

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinitions(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    private void registerBean(Class<?> annotatedClass) {
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new DefaultBeanDefinition(annotatedClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
        for (Method beanMethod : beanMethods) {
            log.debug("@Bean method : {}", beanMethod);
            final Class<?> returnType = getReturnType(beanMethod);
            AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(returnType, beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
        }
    }

    private Class<?> getReturnType(final Method beanMethod) {
        final Class<?> returnType = beanMethod.getReturnType();
        if (returnType.isAssignableFrom(FactoryBean.class)) {
            return getReturnGenericType(beanMethod);
        }

        return returnType;
    }

    private Class<?> getReturnGenericType(final Method beanMethod) {
        final Type genericReturnType = beanMethod.getGenericReturnType();
        final ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }
}
