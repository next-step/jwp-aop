package core.aop;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private T target;
    private final Advisor advisor;

    public ProxyFactoryBean(T target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() throws Exception {
        if (availableJDKDynamicProxy()) {
            return (T) new JDKAopDynamicProxy(target, advisor).proxy();
        }
        return (T) new CGLibAopProxy(target, advisor).proxy();
    }

    private boolean availableJDKDynamicProxy() {
        return target.getClass().getInterfaces().length > 0;
    }
}
