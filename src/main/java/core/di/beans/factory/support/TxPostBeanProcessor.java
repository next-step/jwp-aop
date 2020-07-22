package core.di.beans.factory.support;

import core.annotation.Transactional;
import core.aop.factorybean.TxProxyFactoryBean;
import core.aop.tx.TxPointcut;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class TxPostBeanProcessor implements PostBeanProcessor {

    private ApplicationContext applicationContext;

    public TxPostBeanProcessor(AnnotationConfigApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean supports(Class<?> clazz, Object bean) {
        TxPointcut txPointcut = new TxPointcut();

        return txPointcut.getClassFilter().matches(clazz) || existsTransactionalMethod(clazz);
    }

    private boolean existsTransactionalMethod(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }

    @Override
    public Object process(Class<?> clazz, Object bean) {
        return new TxProxyFactoryBean(applicationContext, bean, clazz).getObject();
    }

}
