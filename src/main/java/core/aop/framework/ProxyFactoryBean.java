package core.aop.framework;

import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.util.ClassUtils;

import core.aop.FactoryBean;

public class ProxyFactoryBean extends AdvisedSupport implements FactoryBean<Object> {

    private Object instance;

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

    private AopProxy createAopProxy() {
        Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(getTargetClass());
        Object target = getTargetSource().getTarget();
        if (interfaces.length == 0) {
            return new CglibAopProxy(target);
        }
        return new JdkDynamicAopProxy(target);
    }

    @Override
    public Class<?> getObjectType() {
        return instance.getClass();
    }
}
