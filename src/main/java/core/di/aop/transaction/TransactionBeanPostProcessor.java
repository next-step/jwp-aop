package core.di.aop.transaction;

import core.annotation.Transactional;
import core.di.aop.ProxyFactoryBean;
import core.di.aop.ProxyPointcutAdvisor;
import core.di.beans.factory.BeanFactory;
import core.di.context.BeanPostProcessor;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.sql.DataSource;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;

    public TransactionBeanPostProcessor(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postInitialization(final Object bean) {
        final Class<?> targetClass = bean.getClass();
        if (hasTransactionalMethod(targetClass)) {
            final TransactionAdvice advice = new TransactionAdvice(beanFactory.getBean(DataSource.class));
            final ProxyPointcutAdvisor advisor = new ProxyPointcutAdvisor(advice, new TransactionPointcut(), beanFactory);
            final ProxyFactoryBean<Object> proxyFactoryBean = new ProxyFactoryBean<>(bean, advisor);
            return proxyFactoryBean.getObject();
        }

        return bean;
    }

    private static boolean hasTransactionalMethod(final Class<?> targetClass) {
        final Method[] declaredMethods = targetClass.getDeclaredMethods();
        return Arrays.stream(declaredMethods)
            .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
