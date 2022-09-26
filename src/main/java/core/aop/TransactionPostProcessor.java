package core.aop;

import core.annotation.Transactional;
import core.di.beans.factory.BeanFactory;

import javax.sql.DataSource;
import java.util.Arrays;

public class TransactionPostProcessor implements BeanPostProcessor {
    private final BeanFactory beanFactory;
    private final PointcutAdvisor advisor;

    public TransactionPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.advisor = new PointcutAdvisor(
                new TransactionAdvice(beanFactory.getBean(DataSource.class)), new TransactionPointCut());
    }

    @Override
    public Object postProcess(Object bean) {
        Class<?> beanClass = bean.getClass();
        if (hasTransactional(beanClass)) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(bean, advisor);
            proxyFactoryBean.setBeanFactory(beanFactory);
            try {
                return proxyFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return bean;
    }

    private boolean hasTransactional(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(Transactional.class));
    }
}
