package core.aop;

import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    protected BeanDefinition beanDefinition;
    protected Class<?> targetClass;
    protected Object[] targetArguments;
    protected Advice advice;
    protected PointCut pointCut;

    public ProxyFactoryBean(BeanDefinition beanDefinition,
                            Object[] targetArguments,
                            Advice advice,
                            PointCut pointCut) {
        this.beanDefinition = beanDefinition;
        this.targetClass = beanDefinition.getBeanClass();
        this.targetArguments = targetArguments;
        this.advice = advice;
        this.pointCut = pointCut;
    }

    @Override
    public T getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new BeanInterceptor(advice, pointCut));

        if (beanDefinition.getInjectConstructor() == null) {
            return (T) enhancer.create();
        }

        Class<?>[] argumentTypes = beanDefinition.getInjectConstructor().getParameterTypes();
        return (T) enhancer.create(argumentTypes, targetArguments);
    }

    // TODO: 2020-07-05 삭제할것
    @Override
    public Class<?> getClassType() {
        return targetClass;
    }
}
