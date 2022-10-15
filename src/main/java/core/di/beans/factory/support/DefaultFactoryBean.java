package core.di.beans.factory.support;

import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.aop.Aspect;
import core.di.beans.factory.aop.CGLibAspect;
import core.di.beans.factory.aop.JdkAspect;
import core.di.beans.factory.aop.Target;
import net.sf.cglib.proxy.Enhancer;
import next.hello.Hello;

import java.lang.reflect.Proxy;

public class DefaultFactoryBean<T> implements FactoryBean<T> {

    private final Target target;
    private final Aspect aspect;

    public DefaultFactoryBean(Target target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }
    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        if (aspect instanceof JdkAspect) {
            return (T) Proxy.newProxyInstance(DefaultFactoryBean.class.getClassLoader(), new Class[]{Hello.class}, (JdkAspect) aspect);
        }
        return (T) Enhancer.create(target.getClazz(), (CGLibAspect) aspect);
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClazz();
    }
}