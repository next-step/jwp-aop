package core.di.aop;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum AopProxyFactory {

    JDK_DYNAMIC_PROXY(JdkDynamicAopProxy::new),
    CGLIB_PROXY((target, advisor) -> new CglibAopProxy(target.getClass(), advisor));

    final BiFunction<Object, PointcutAdvisor, AopProxy> function;

    AopProxyFactory(final BiFunction<Object, PointcutAdvisor, AopProxy> function) {
        this.function = function;
    }

    public static AopProxy of(final Object target, final PointcutAdvisor advisor) {
        return Arrays.stream(values())
            .filter(it -> it.isImplemented(target))
            .findAny()
            .map(it -> it.getAopProxy(target, advisor))
            .orElse(CGLIB_PROXY.getAopProxy(target, advisor));
    }

    private boolean isImplemented(final Object target) {
        return target.getClass().getInterfaces().length > 0;
    }

    private AopProxy getAopProxy(final Object target, final PointcutAdvisor advisor) {
        return this.function.apply(target, advisor);
    }
}
