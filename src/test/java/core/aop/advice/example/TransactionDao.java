package core.aop.advice.example;

import core.annotation.Component;
import core.aop.ProxyFactoryBean;
import core.aop.advice.TransactionAdvice;
import core.aop.pointcut.BypassPointCut;
import core.di.beans.factory.config.BeanDefinition;

@Component
public class TransactionDao extends ProxyFactoryBean<Dao> {
    public TransactionDao(BeanDefinition beanDefinition, Object[] arguments) {
        super(
                beanDefinition,
                arguments,
                new TransactionAdvice(),
                new BypassPointCut()
        );
    }
}
