package core.aop.example.di;

import core.annotation.Component;
import core.aop.Advice;
import core.aop.PointCut;
import core.aop.TempProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;

@Component
public class ProxyClass extends TempProxyFactoryBean<TargetClass> {
    public ProxyClass(BeanDefinition beanDefinition, Object... arguments) {
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
        return (method, targetClass, arguments) -> method.getName().startsWith("get");
    }

    @Override
    public Class<?> getClassType() {
        return TargetClass.class;
    }
}
