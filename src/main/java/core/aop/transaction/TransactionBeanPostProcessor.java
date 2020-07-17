package core.aop.transaction;

import core.aop.ProxyFactoryBean;
import core.di.beans.factory.BeanPostProcessor;
import java.util.Arrays;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcess(Object bean) {
        try {
            return new ProxyFactoryBean(bean, Arrays.asList(new TransactionAdvice())).getObject();
        } catch (Exception e) {
            return bean;
        }
    }
}
