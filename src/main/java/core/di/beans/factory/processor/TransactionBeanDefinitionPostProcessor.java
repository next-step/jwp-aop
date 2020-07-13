package core.di.beans.factory.processor;

import core.annotation.Transactional;
import core.di.beans.factory.definition.BeanDefinition;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author KingCjy
 */
public class TransactionBeanDefinitionPostProcessor implements BeanDefinitionPostProcessor {

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return isTransactionalAnnotatedClass(beanDefinition.getType()) || !getTransactionalAnnotatedMethods(beanDefinition.getType()).isEmpty();
    }

    @Override
    public BeanDefinition process(BeanDefinition beanDefinition) {

        return null;
    }

    private boolean isTransactionalAnnotatedClass(Class<?> targetClass) {
        return targetClass.isAnnotationPresent(Transactional.class);
    }

    private Set<Method> getTransactionalAnnotatedMethods(Class<?> targetClass) {
        return Arrays.stream(targetClass.getMethods())
                .filter(method -> method.isAnnotationPresent(Transactional.class))
                .collect(Collectors.toSet());
    }
}
