package core.di.aop;

import core.di.beans.factory.BeanFactory;

public class ProxyPointcutAdvisor extends PointcutAdvisor implements Advisor {

    private final BeanFactory beanFactory;

    public ProxyPointcutAdvisor(final Advice advice, final Pointcut pointcut, final BeanFactory beanFactory) {
        super(advice, pointcut);
        this.beanFactory = beanFactory;
    }

    public Object getBean(Class<?> clazz) {
        if (beanFactory == null) {
            return null;
        }
        return beanFactory.getBean(clazz);
    }

}
