package core.di.beans.factory.config.converter;

import core.annotation.Transactional;
import core.aop.Advice;
import core.aop.ProxyBeanDefinition;
import core.aop.advice.TransactionAdvice;
import core.aop.pointcut.BypassPointCut;
import core.aop.pointcut.MethodMatchPointCut;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.config.BeanDefinitionConverter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionBeanDefinitionConverter implements BeanDefinitionConverter {

    @Override
    public boolean support(Class<?> clazz) {
        return isTransactionalAnnotatedClass(clazz) ||
                !getTransactionalAnnotatedMethods(clazz).isEmpty();
    }

    @Override
    public BeanDefinition convert(Class<?> clazz) {
        Advice advice = new TransactionAdvice();

        if (isTransactionalAnnotatedClass(clazz)) {
            return new ProxyBeanDefinition(clazz, advice, new BypassPointCut());
        }

        return new ProxyBeanDefinition(
                clazz,
                advice,
                new MethodMatchPointCut(getTransactionalAnnotatedMethods(clazz))
        );
    }

    private boolean isTransactionalAnnotatedClass(Class<?> clazz) {
        return clazz.isAnnotationPresent(Transactional.class);
    }

    private Set<Method> getTransactionalAnnotatedMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Transactional.class))
                .collect(Collectors.toSet());
    }
}
