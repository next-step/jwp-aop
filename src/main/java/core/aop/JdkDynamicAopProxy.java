package core.aop;

public class JdkDynamicAopProxy implements Proxy {

    private final Class<?> targetClass;
    private final Object targetSource;
    private final Advisor advisor;

    public JdkDynamicAopProxy(Class<?> targetClass, Object targetSource, Advisor advisor) {
        this.targetClass = targetClass;
        this.targetSource = targetSource;
        this.advisor = advisor;
    }

    @Override
    public Object proxy() {
        return java.lang.reflect.Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                targetSource.getClass().getInterfaces(),
                (proxy, method, args) -> new AdvisorMethodInvocation(advisor, targetClass, method, () -> method.invoke(targetSource, args)).proceed()
        );

    }
}
