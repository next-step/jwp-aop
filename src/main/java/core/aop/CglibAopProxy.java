package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibAopProxy implements Proxy{

    private final Enhancer enhancer = new Enhancer();
    private final Class<?> targetClass;
    private final Advisor advisor;

    public CglibAopProxy(Class<?> targetClass, Advisor advisor) {
        this.targetClass = targetClass;
        this.advisor = advisor;
    }

    @Override
    public Object proxy() {
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(
                (MethodInterceptor) (obj, method, args, proxy) -> new AdvisorMethodInvocation(advisor, targetClass, method, () -> proxy.invokeSuper(obj, args)).proceed()
        );

        return enhancer.create();
    }
}
