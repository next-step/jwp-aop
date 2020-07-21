package core.aop.factorybean;

import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@NoArgsConstructor
public abstract class ProxyFactoryBean implements FactoryBean<Object> {

    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{getObjectType()},
                getInvocationHandler());
    }

    public abstract Class<?> getObjectType();

    protected abstract InvocationHandler getInvocationHandler();

}
