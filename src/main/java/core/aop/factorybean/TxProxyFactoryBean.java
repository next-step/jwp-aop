package core.aop.factorybean;

import core.aop.tx.TxMethodInterceptor;
import core.aop.tx.TxPointcut;
import core.di.context.ApplicationContext;
import net.sf.cglib.proxy.MethodInterceptor;

public class TxProxyFactoryBean extends ProxyFactoryBean {

    private ApplicationContext applicationContext;

    public TxProxyFactoryBean(ApplicationContext applicationContext, Object target, Class<?> objectType) {
        super(applicationContext, target, objectType);
        this.applicationContext = applicationContext;
    }

    @Override
    protected MethodInterceptor getMethodInterceptor() {
        return new TxMethodInterceptor(applicationContext, new TxPointcut());
    }

}
