package core.transaction;

import core.aop.Advisor;
import core.aop.ProxyFactoryBean;
import core.di.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;
import java.util.stream.Stream;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private static final TransactionPointCut POINT_CUT = TransactionPointCut.instance();

    private final TransactionInterceptor interceptor;

    public TransactionBeanPostProcessor(DataSource dataSource) {
        this.interceptor = TransactionInterceptor.from(dataSource);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) {
        if (POINT_CUT.matches(bean.getClass()) || anyMatchesMethod(bean.getClass())) {
            return ProxyFactoryBean.of(bean.getClass(), bean, Advisor.of(interceptor, POINT_CUT));
        }
        return bean;
    }

    private boolean anyMatchesMethod(Class<?> beanClass) {
        return Stream.of(beanClass.getMethods())
                .anyMatch(POINT_CUT::matches);
    }
}
