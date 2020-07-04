package core.aop.example;

import core.annotation.Component;
import core.aop.Advice;
import core.aop.PointCut;
import core.aop.ProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;

@Component
public class Proxy extends ProxyFactoryBean<SimpleTarget> {

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
