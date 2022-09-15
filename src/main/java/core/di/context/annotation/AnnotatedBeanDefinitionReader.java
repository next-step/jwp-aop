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
            final Class<?> returnType = returnType(beanMethod);
            AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(returnType, beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
        }
    }

    private Class<?> returnType(final Method beanMethod) {
        final Class<?> returnType = beanMethod.getReturnType();
        if (returnType.isAssignableFrom(FactoryBean.class)) {
            final Type genericReturnType = beanMethod.getGenericReturnType();
            final ParameterizedType genericReturnType1 = (ParameterizedType) genericReturnType;
            return (Class<?>) genericReturnType1.getActualTypeArguments()[0];
        }

        return returnType;
    }
}
