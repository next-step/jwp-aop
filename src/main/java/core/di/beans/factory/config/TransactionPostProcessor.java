package core.di.beans.factory.config;

import java.util.Arrays;

import javax.sql.DataSource;

import core.annotation.Transactional;
import core.aop.framework.ProxyFactoryBean;
import core.aop.support.TransactionAdvisor;
import core.di.beans.factory.BeanFactory;

public class TransactionPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;
    private final TransactionAdvisor advisor;

    public TransactionPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.advisor = new TransactionAdvisor(beanFactory.getBean(DataSource.class));
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) {
        Class<?> beanClass = bean.getClass();
        if (hasTransactionalAnnotation(beanClass)) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(bean);
            proxyFactoryBean.setBeanFactory(beanFactory);
            proxyFactoryBean.addAdvisor(advisor);
            return proxyFactoryBean.getObject();
        }
        return bean;
    }

    private boolean hasTransactionalAnnotation(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
