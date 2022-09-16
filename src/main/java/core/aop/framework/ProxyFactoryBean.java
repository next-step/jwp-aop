package core.aop.framework;

import org.springframework.util.ClassUtils;

import core.aop.FactoryBean;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object getObject() {
        AopProxy aopProxy = createAopProxy();
        return aopProxy.getProxy();
    }

    private AopProxy createAopProxy() {
        Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(target.getClass());
        if (interfaces.length == 0) {
            return new CglibAopProxy(target);
        }
        return new JdkDynamicAopProxy(target);
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }
}
