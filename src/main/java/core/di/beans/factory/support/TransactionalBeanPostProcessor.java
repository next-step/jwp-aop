package core.di.beans.factory.support;

import core.annotation.Transactional;
import core.aop.Advisor;
import core.aop.TransactionalAdvice;
import core.aop.TransactionalPointCut;
import core.di.beans.ProxyFactoryBean;

import javax.sql.DataSource;
import java.util.Arrays;

public class TransactionalBeanPostProcessor implements BeanPostProcessor {
    private static final TransactionalPointCut TRANSACTIONAL_POINT_CUT = TransactionalPointCut.getInstance();

    private final TransactionalAdvice advice;

    public TransactionalBeanPostProcessor(DataSource dataSource) {
        this.advice = new TransactionalAdvice(dataSource);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) {
        Class<?> targetClass = bean.getClass();
        if (hasTransactionalAnnotation(targetClass)) {
            ProxyFactoryBean<Object> proxyFactoryBean = new ProxyFactoryBean<>(bean, new Advisor(advice, TRANSACTIONAL_POINT_CUT));
            return proxyFactoryBean.getObject();
        }
        return bean;
    }

    private boolean hasTransactionalAnnotation(Class<?> targetClass) {
        if (targetClass.isAnnotationPresent(Transactional.class)) {
            return true;
        }
        return Arrays.stream(targetClass.getDeclaredMethods())
                .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
