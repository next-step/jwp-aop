package next.service;

import core.annotation.Component;
import core.di.beans.factory.support.MethodInvocation;
import core.di.beans.factory.support.MethodMatcher;
import core.di.beans.factory.support.ProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Component
public class UserLogServiceFactoryBean extends ProxyFactoryBean<UserLogService> {

    private static final Logger logger = LoggerFactory.getLogger(UserLogServiceFactoryBean.class);

    @Override
    protected MethodMatcher matcher() {
        return (m, returnType, args) -> m.getName().startsWith("log");
    }

    @Override
    protected MethodInvocation methodInvocation() {
        return proxyInvocation -> {
            StopWatch sw = new StopWatch();
            sw.start();
            final Object result = proxyInvocation.proceed();
            sw.stop();
            logger.info(sw.prettyPrint());
            return result;
        };
    }

    @Override
    public Class<?> getType() {
        return UserLogService.class;
    }
}
