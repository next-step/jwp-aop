package core.aop;

import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.InjectType;
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

        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            Class aClass = enhancer.createClass();
        }

        if (beanDefinition.getInjectConstructor() == null) {
            return (T) enhancer.create(); //생성자를 통한 생성
        }

        Class<?>[] argumentTypes = beanDefinition.getInjectConstructor().getParameterTypes();
        return (T) enhancer.create(argumentTypes, targetArguments); // argument type에 맞는 생성자를 찾아 생성
    }

    @Override
    public Class<?> getClassType() {
        return targetClass;
    }
}
