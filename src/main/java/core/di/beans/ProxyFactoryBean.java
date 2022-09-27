package core.di.beans;

import core.aop.Advisor;
import core.aop.AopProxyFactory;
import core.di.beans.factory.FactoryBean;
import org.springframework.util.Assert;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    private final T target;
    private final Advisor advisor;

    public ProxyFactoryBean(T target, Advisor advisor) {
        Assert.notNull(target, "타겟 클래스는 null 일 수 없습니다.");
        Assert.notNull(advisor, "어드바이저는 null 일 수 없습니다.");
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public T getObject() {
        return (T) AopProxyFactory.getInstance().createAopProxy(this.target, this.advisor).getProxy();
    }

    @Override
    public Class<T> getObjectType() {
        return (Class<T>) this.target.getClass();
    }
}
