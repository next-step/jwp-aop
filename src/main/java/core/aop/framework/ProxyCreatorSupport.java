package core.aop.framework;

public class ProxyCreatorSupport extends AdvisedSupport {

    private final AopProxyFactory aopProxyFactory;

    public ProxyCreatorSupport(Object target) {
        super(target);
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(this);
    }

    private AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }
}
