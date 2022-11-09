package core.transaction;

import core.aop.Advisor;
import core.aop.AopProxyFactoryBean;
import core.di.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;
import java.util.stream.Stream;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private static final TransactionPointCut POINT_CUT = TransactionPointCut.getInstance();
    private final TransactionalInterceptor interceptor;

    public TransactionBeanPostProcessor(DataSource dataSource) {
        this.interceptor = new TransactionalInterceptor(dataSource);
    }

    @Override
    public Object postProcess(Object bean) {
        if (anyMatches(bean.getClass())) {
            return new AopProxyFactoryBean<>(bean.getClass(), bean, new Advisor(interceptor, POINT_CUT));
        }
        return bean;
    }

    private boolean anyMatches(Class<?> beanClass) {
        return Stream.of(beanClass.getMethods())
                .anyMatch(method -> POINT_CUT.matches(beanClass, method));
    }


}
