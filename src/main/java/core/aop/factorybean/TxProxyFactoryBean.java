package core.aop.factorybean;

import core.aop.tx.TxInvocationHandler;
import core.aop.tx.TxPointcut;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;

@RequiredArgsConstructor
public class TxProxyFactoryBean extends ProxyFactoryBean {

    private final Object target;
    private final Class<?> objectType;

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    @Override
    protected InvocationHandler getInvocationHandler() {
        return new TxInvocationHandler(target, new TxPointcut());
    }

}
