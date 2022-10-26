package core.aop;

public class AopProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> targetClass;
    private final T targetSource;
    private final Advisor advisor;

    private final AopProxyFactory aopProxyFactory;

    public AopProxyFactoryBean(Class<T> targetClass,
                               T targetSource,
                               Advisor advisor) {
        this.targetClass = targetClass;
        this.targetSource = targetSource;
        this.advisor = advisor;
        this.aopProxyFactory = new AopProxyFactory();
    }
    @Override
    public T object() {
        return (T) aopProxyFactory.createAopProxy(targetClass, targetSource, advisor);
    }
}
