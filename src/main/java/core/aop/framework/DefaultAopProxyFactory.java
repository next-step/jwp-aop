package core.aop.framework;

import java.lang.reflect.Proxy;

import org.springframework.util.ClassUtils;

public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) {
        if (config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
            Class<?> targetClass = config.getTargetClass();
            if (targetClass == null) {
                throw new RuntimeException();
            }
            if (targetClass.isInterface() || Proxy.isProxyClass(targetClass) || ClassUtils.isLambdaClass(targetClass)) {
                return new JdkDynamicAopProxy(config);
            }
            return new CglibAopProxy(config);
        }
        return new JdkDynamicAopProxy(config);
    }

    private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
        return config.getProxiedInterfaces().length == 0;
    }
}
