package core.aop.example.di;

import core.annotation.Component;
import core.aop.ProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;

@Component
public class ProxyClass extends ProxyFactoryBean<TargetClass> {
    public ProxyClass(BeanDefinition beanDefinition, Object... arguments) {
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
                (method, targetClass, args) -> method.getName().startsWith("get")
        );
    }
}
