package core.aop;

import java.lang.reflect.Proxy;

final class DefaultAopProxyFactory {

    private DefaultAopProxyFactory() {
    }

    static DefaultAopProxyFactory instance() {
        return DefaultAopProxyFactoryHolder.INSTANCE;
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

    private static class DefaultAopProxyFactoryHolder {
        private static final DefaultAopProxyFactory INSTANCE = new DefaultAopProxyFactory();
    }
}
