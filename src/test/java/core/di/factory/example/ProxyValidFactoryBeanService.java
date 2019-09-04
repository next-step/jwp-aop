package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.support.MethodInvocation;
import core.di.beans.factory.support.MethodMatcher;
import core.di.beans.factory.support.ProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProxyValidFactoryBeanService extends ProxyFactoryBean<FactoryTestService> {

    private static final Logger logger =
            LoggerFactory.getLogger(ProxyValidFactoryBeanService.class);

    public static boolean isBeforeExecuted = false;
    public static boolean isAfterExecuted = false;

    @Override
    protected MethodMatcher matcher() {
        return (m, returnType, args) -> m.getName().startsWith("log");
    }

    @Override
    protected MethodInvocation methodInvocation() {
        return proxyInvocation -> {
            logger.info("before executed");
            isBeforeExecuted = true;
            Object result = proxyInvocation.proceed();
            logger.info("after executed");
            isAfterExecuted = true;
            return result;
        };
    }

    @Override
    public Class<?> getType() {
        return FactoryTestService.class;
    }
}
