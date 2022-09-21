package core.di.beans.factory.aop.processor;

import core.annotation.Transactional;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.aop.Aspect;
import core.di.beans.factory.aop.ProxyFactoryBean;
import core.di.beans.factory.aop.advisor.Target;
import core.di.beans.factory.aop.advisor.TransactionalAdvice;
import core.di.beans.factory.aop.advisor.TransactionalPointcut;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class TransactionBeanPostProcessor implements BeanPostProcessor {
    private final int priority;

    public TransactionBeanPostProcessor(int priority) {
        this.priority = priority;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public boolean supports(Class<?> clazz, Object instance) {
        if (clazz.isAnnotationPresent(Transactional.class)) {
            return true;
        }

        return Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method-> method.isAnnotationPresent(Transactional.class));
    }

    @Override
    public void action(Class<?> clazz, Object instance, BiConsumer<Class<?>, Object> consumer, BeanFactory ac) {
        try {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(new Target(instance, clazz));
            proxyFactoryBean.setAspect(new Aspect(new TransactionalPointcut(), new TransactionalAdvice(ac.getBean(DataSource.class))));
            proxyFactoryBean.setBeanFactory(ac);

            consumer.accept(clazz, proxyFactoryBean.getObject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
