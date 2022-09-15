package core.aop;

import org.springframework.util.Assert;

public final class ProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<? extends T> targetClass;
    private final T targetSource;
    private final Advisor advisor;

    private ProxyFactoryBean(Class<? extends T> targetClass, T targetSource, Advisor advisor) {
        Assert.notNull(targetClass, "'targetClass' must not be null");
        Assert.notNull(targetSource, "'targetSource' must not be null");
        Assert.notNull(advisor, "'advisor' must not be null");
        this.targetClass = targetClass;
        this.targetSource = targetSource;
        this.advisor = advisor;
    }

    public static <T> ProxyFactoryBean<T> of(Class<? extends T> targetClass, T targetSource, Advisor advisor) {
        return new ProxyFactoryBean<>(targetClass, targetSource, advisor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T object() {
        return (T) CglibAopProxy.of(targetClass, targetSource, advisor).proxy();
    }
}
