package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.util.Assert;

final class CglibAopProxy implements AopProxy {

    private final Enhancer enhancer = new Enhancer();
    private final Class<?> targetClass;
    private final Advisor advisor;

    private CglibAopProxy(Class<?> targetClass, Advisor advisor) {
        Assert.notNull(targetClass, "'targetClass' must not be null");
        Assert.notNull(advisor, "'advisor' must not be null");
        this.targetClass = targetClass;
        this.advisor = advisor;
    }

    static CglibAopProxy of(Class<?> targetClass, Advisor advisor) {
        return new CglibAopProxy(targetClass, advisor);
    }

    @Override
    public Object proxy() {
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) ->
                AdvisorMethodInvocation.of(advisor, targetClass, method, () -> proxy.invokeSuper(obj, args)).proceed());
        return enhancer.create();
    }
}
