package core.aop;

import java.lang.reflect.Proxy;

public class AopProxyFactory {

    public AopProxyFactory() {
    }

    public Object createAopProxy(Class<?> targetClass, Object source, Advisor advisor) {
        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
            return new JdkDynamicAopProxy(targetClass, source, advisor).proxy();
        }

        return new CglibAopProxy(targetClass, advisor).proxy();
    }
}
