package core.aop;

import org.springframework.util.Assert;

import java.lang.reflect.Proxy;

final class JdkDynamicAopProxy implements AopProxy {

    private final Class<?> targetClass;
    private final Object targetSource;
    private final Advisor advisor;

    private JdkDynamicAopProxy(Class<?> targetClass, Object targetSource, Advisor advisor) {
        Assert.notNull(targetClass, "'targetClass' must not be null");
        Assert.notNull(targetSource, "'targetSource' must not be null");
        Assert.notNull(advisor, "'advisor' must not be null");
        this.targetClass = targetClass;
        this.targetSource = targetSource;
        this.advisor = advisor;
    }

    static JdkDynamicAopProxy of(Class<?> targetClass, Object targetSource, Advisor advisor) {
        return new JdkDynamicAopProxy(targetClass, targetSource, advisor);
    }

    @Override
    public Object proxy() {
        return Proxy.newProxyInstance(
                defaultClassLoader(),
                targetSource.getClass().getInterfaces(),
                (proxy, method, args) ->
                        AdvisorMethodInvocation.of(advisor, targetClass, method,
                                () -> method.invoke(targetSource, args)).proceed()
        );
    }

    private ClassLoader defaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
