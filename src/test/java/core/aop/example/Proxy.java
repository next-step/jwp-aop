package core.aop.example;

import core.aop.Advice;
import core.aop.PointCut;
import core.aop.TempProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;

public class Proxy extends TempProxyFactoryBean<SimpleTarget> {

    public Proxy(BeanDefinition beanDefinition, Object... arguments) {
        super(beanDefinition, arguments);
    }

    @Override
    protected Advice advice() {
        return (object, method, arguments, proxy) -> {
            try {
                return ((String) proxy.invokeSuper(object, arguments)).toUpperCase();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        };
    }

    @Override
    protected PointCut pointCut() {
        return (method, targetClass, arguments) -> method.getReturnType().isAssignableFrom(String.class);
    }

    @Override
    public Class<?> getClassType() {
        return SimpleTarget.class;
    }
}
