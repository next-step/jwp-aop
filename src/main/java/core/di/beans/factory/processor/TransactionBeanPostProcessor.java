package core.di.beans.factory.processor;

import core.annotation.Transactional;
import core.aop.Advice;
import core.aop.ProxyFactoryBean;
import core.aop.support.MethodMatchPointcut;
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
public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private TransactionalAdvice advice;

    public TransactionBeanPostProcessor(BeanFactory beanFactory) {
        DataSource dataSource = beanFactory.getBean(DataSource.class);
        this.advice = new TransactionalAdvice(dataSource);
    }

    @Override
    public Object postProcess(BeanDefinition beanDefinition, Object bean) throws Exception {
        if(!isTransactionBean(beanDefinition.getType())) {
            return bean;
        }

        MethodMatchPointcut pointcut = new MethodMatchPointcut(getTransactionMethods(beanDefinition.getType()));
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean<>(bean, pointcut, advice);

        return proxyFactoryBean.getObject();
    }

    private boolean isTransactionBean(Class<?> targetClass) {
        return targetClass.isAnnotationPresent(Transactional.class) || !getTransactionMethods(targetClass).isEmpty();
    }

    private Set<Method> getTransactionMethods(Class<?> targetClass) {
        return Arrays.stream(targetClass.getMethods())
                .filter(method -> method.isAnnotationPresent(Transactional.class))
                .collect(Collectors.toSet());
    }
}
