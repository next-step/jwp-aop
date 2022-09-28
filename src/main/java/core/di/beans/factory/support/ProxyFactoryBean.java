package core.di.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    private final T target;
    private final Advisor advisor;

    public ProxyFactoryBean(T target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        final Advice advice = advisor.getAdvice();
        final PointCut pointCut = advisor.getPointCut();
        return (T) Enhancer.create(target.getClass(), advice.getMethodInterceptor(pointCut));
    }
}
