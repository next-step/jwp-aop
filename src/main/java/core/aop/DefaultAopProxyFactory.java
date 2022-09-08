package core.aop;

import java.lang.reflect.Proxy;

final class DefaultAopProxyFactory {

    private static final DefaultAopProxyFactory INSTANCE = new DefaultAopProxyFactory();

    private DefaultAopProxyFactory() {
        if (INSTANCE != null) {
            throw new AssertionError(String.format("%s is singleton", getClass()));
        }
    }

    static DefaultAopProxyFactory instance() {
        return INSTANCE;
    }

    Object newProxy(Class<?> targetClass, Object source, Advisor advisor) {
        return aopProxy(targetClass, source, advisor).proxy();
    }

    private AopProxy aopProxy(Class<?> targetClass, Object source, Advisor advisor) {
        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
            return JdkDynamicAopProxy.of(targetClass, source, advisor);
        }
        return CglibAopProxy.of(targetClass, advisor);
    }
}
