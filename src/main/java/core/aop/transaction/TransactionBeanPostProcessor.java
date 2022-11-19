package core.aop.transaction;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

import javax.sql.DataSource;

import core.annotation.Transactional;
import core.di.beans.factory.ProxyFactoryBean;
import core.di.beans.factory.proxy.Advisor;
import core.di.beans.factory.proxy.cglib.CglibProxyFactory;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private final DataSource dataSource;

    public TransactionBeanPostProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object process(Object bean) {
        Class<?> target = bean.getClass();

        if (hasTransaction(target)) {
            var transactionAdvice = new TransactionAdvice(dataSource);
            var transactionPointcut = new TransactionPointcut();

            ProxyFactoryBean<Object> transactionProxyBean = new ProxyFactoryBean<>(
                (Class<Object>)target,
                bean,
                new Advisor(transactionAdvice, transactionPointcut),
                new CglibProxyFactory()
            );

            return createObject(transactionProxyBean);
        }

        return bean;
    }

    private boolean hasTransaction(Class<?> target) {
        BooleanSupplier hasMethodTransactional = () -> Arrays.stream(target.getDeclaredMethods())
            .anyMatch(it -> it.isAnnotationPresent(Transactional.class));

        return target.isAnnotationPresent(Transactional.class)
            || hasMethodTransactional.getAsBoolean();
    }

    private Object createObject(ProxyFactoryBean<Object> proxyFactoryBean) {
        try {
            return proxyFactoryBean.getObject();
        } catch (Exception e) {
            throw new IllegalStateException("프록시를 생성할 수 없습니다", e);
        }
    }
}
