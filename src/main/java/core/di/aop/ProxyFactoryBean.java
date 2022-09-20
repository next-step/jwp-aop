package core.di.aop;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final T target;
    private final PointcutAdvisor advisor;

    public ProxyFactoryBean(final T target, final PointcutAdvisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() throws Exception {
        return (T) AopProxyFactory.of(target, advisor).getProxy();
    }

    @Override
    public Class<T> getObjectType() {
        return (Class<T>) target.getClass();
    }

}
