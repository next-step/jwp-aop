package core.aop;

import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;

public abstract class TempProxyFactoryBean<T> implements FactoryBean<T> {
    protected Object[] targetArguments;
    protected BeanDefinition beanDefinition;

    public TempProxyFactoryBean(BeanDefinition beanDefinition, Object... arguments) {
        this.beanDefinition = beanDefinition;
        this.targetArguments = arguments;
    }

    @Override
    public T getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getClassType());
        enhancer.setCallback(new BeanInterceptor(advice(), pointCut()));

        if (beanDefinition.getInjectConstructor() == null) {
            return (T) enhancer.create();
        }

        Class<?>[] argumentTypes = beanDefinition.getInjectConstructor().getParameterTypes();
        return (T) enhancer.create(argumentTypes, targetArguments);
    }

    protected abstract Advice advice();
    protected abstract PointCut pointCut();

}
