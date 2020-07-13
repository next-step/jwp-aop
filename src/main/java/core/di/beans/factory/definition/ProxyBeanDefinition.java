package core.di.beans.factory.definition;

import core.aop.Advice;
import core.aop.Pointcut;

/**
 * @author KingCjy
 */
public class ProxyBeanDefinition implements BeanDefinition {
    private Pointcut pointcut;
    private Advice advice;
    private BeanDefinition beanDefinition;

    public ProxyBeanDefinition(BeanDefinition beanDefinition, Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public Class<?> getType() {
        return beanDefinition.getType();
    }

    @Override
    public String getName() {
        return beanDefinition.getName();
    }
}
