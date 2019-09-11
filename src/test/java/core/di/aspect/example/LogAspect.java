package core.di.aspect.example;

import core.annotation.Around;
import core.annotation.Aspect;
import core.annotation.Component;
import core.annotation.Ordered;
import core.di.aspect.AspectConfig;
import core.di.beans.factory.support.ProxyInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
@Ordered(order = 2)
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    public static int BEFORE_COUNT = -1;
    public static int AFTER_COUNT = -1;

    @Around(pointcut = "core.di.aspect.example.AspectUserService*")
    public Object logging(ProxyInvocation invocation) {
        BEFORE_COUNT = AspectConfig.COUNT.incrementAndGet();
        logger.debug("## [Log] before invoked");
        final Object result = invocation.proceed();
        logger.debug("## [Log] after invoked");
        AFTER_COUNT = AspectConfig.COUNT.incrementAndGet();
        return result;
    }

}
