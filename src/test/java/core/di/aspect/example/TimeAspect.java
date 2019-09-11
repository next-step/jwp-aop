package core.di.aspect.example;

import core.annotation.Around;
import core.annotation.Aspect;
import core.annotation.Component;
import core.annotation.Ordered;
import core.di.aspect.AspectConfig;
import core.di.beans.factory.support.ProxyInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
@Ordered(order = 3)
public class TimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(TimeAspect.class);
    public static int BEFORE_COUNT = -1;
    public static int AFTER_COUNT = -1;

    @Around(pointcut = "core.di.aspect.example.AspectUserService.addAndGetUser")
    public Object elapsedTime(ProxyInvocation invocation) {
        BEFORE_COUNT = AspectConfig.COUNT.incrementAndGet();
        long startTime = System.currentTimeMillis();
        logger.debug("## [Time] before invoked, class name");
        final Object result = invocation.proceed();
        logger.debug("## [Time] after invoked, elapsedTime: {} millis",
                System.currentTimeMillis() - startTime);
        AFTER_COUNT = AspectConfig.COUNT.incrementAndGet();
        return result;
    }

}
