package core.aop;

import org.springframework.util.Assert;

public final class ProxyFactoryBean<T> implements FactoryBean<T> {

    private static final DefaultAopProxyFactory PROXY_FACTORY = DefaultAopProxyFactory.instance();

    private final Class<T> targetClass;
    private final T targetSource;
    private final Advisor advisor;

    private ProxyFactoryBean(Class<T> targetClass, T targetSource, Advisor advisor) {
        Assert.notNull(targetClass, "'targetClass' must not be null");
        Assert.notNull(targetSource, "'targetSource' must not be null");
        Assert.notNull(advisor, "'advisor' must not be null");
        this.targetClass = targetClass;
        this.targetSource = targetSource;
        this.advisor = advisor;
    }

    public static <T> ProxyFactoryBean<T> of(Class<T> targetClass, T targetSource, Advisor advisor) {
        return new ProxyFactoryBean<>(targetClass, targetSource, advisor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T object() {
        return (T) PROXY_FACTORY.newProxy(targetClass, targetSource, advisor);
    }
}
