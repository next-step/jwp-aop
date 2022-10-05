package core.aop.factorybean;

import core.aop.Advisor;
import core.aop.CGLIBProxy;
import core.aop.FactoryBean;
import core.aop.JDKProxy;

public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final T target;
    private final Advisor advisor;

    public ProxyFactoryBean(T target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() {
        if (target.getClass().getInterfaces().length > 0) {
            return (T) new JDKProxy(target, advisor).proxy();
        }
        return (T) new CGLIBProxy(target, advisor).proxy();
    }
}
