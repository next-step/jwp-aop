package core.aop;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final T target;
    private final AbstractAopAdvisor advisor;

    public ProxyFactoryBean(T target, AbstractAopAdvisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() throws Exception {
        if (availableCGLibDynamicProxy()) {
            return (T) new CGLibAopProxy(target, advisor).proxy();
        }
        return (T) new JDKAopDynamicProxy(target, advisor).proxy();
    }

    private boolean availableCGLibDynamicProxy() {
        return target.getClass().getInterfaces().length == 0;
    }
}
