package core.aop.example;

import core.aop.ProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;

public class Proxy extends ProxyFactoryBean<SimpleTarget> {

    public Proxy(BeanDefinition beanDefinition, Object... arguments) {
        super(
                beanDefinition,
                arguments,
                (object, method, args, proxy) -> {
                    try {
                        return ((String) proxy.invokeSuper(object, args)).toUpperCase();
                    } catch (Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                },
                (method, targetClass, args) -> method.getReturnType().isAssignableFrom(String.class)
        );
    }
}
