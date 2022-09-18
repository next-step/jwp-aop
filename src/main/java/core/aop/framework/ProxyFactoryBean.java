package core.aop.framework;

import org.springframework.beans.factory.FactoryBeanNotInitializedException;

import core.aop.FactoryBean;

public class ProxyFactoryBean extends ProxyCreatorSupport implements FactoryBean<Object> {

    private Object instance;

    public ProxyFactoryBean(Object target) {
        super(target);
    }

    @Override
    public Object getObject() {
        if (this.instance == null) {
            Class<?> targetClass = getTargetClass();
            if (targetClass == null) {
                throw new FactoryBeanNotInitializedException();
            }
            AopProxy aopProxy = createAopProxy();
            this.instance = aopProxy.getProxy();
        }
        return this.instance;
    }

    @Override
    public Class<?> getObjectType() {
        return instance.getClass();
    }
}
