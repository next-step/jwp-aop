package core.aop.framework;

public interface AopProxyFactory {

    AopProxy createAopProxy(AdvisedSupport config);
}
