package core.di.beans.factory.processor;

import core.annotation.Transactional;
import core.aop.Advice;
import core.aop.ProxyFactoryBean;
import core.aop.support.TransactionalAdvice;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.BeanFactoryAware;
import core.di.beans.factory.definition.BeanDefinition;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author KingCjy
 */
public class TransactionBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private BeanFactory beanFactory;
    private Advice advice;

    public TransactionBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.advice = new TransactionalAdvice(beanFactory.getBean(DataSource.class));
    }

    @Override
    public Object postProcess(BeanDefinition beanDefinition, Object bean) {
        if(!isTransactionBean(beanDefinition.getType())) {
            return bean;
        }

//        ProxyFactoryBean<Object> proxyFactoryBean = new ProxyFactoryBean<>();

        return null;
    }

    private boolean isTransactionBean(Class<?> targetClass) {
        return targetClass.isAnnotationPresent(Transactional.class) || !getTransactionMethods(targetClass).isEmpty();
    }

    private Set<Method> getTransactionMethods(Class<?> targetClass) {
        return Arrays.stream(targetClass.getMethods())
                .filter(method -> method.isAnnotationPresent(Transactional.class))
                .collect(Collectors.toSet());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
