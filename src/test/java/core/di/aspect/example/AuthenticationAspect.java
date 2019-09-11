package core.di.aspect.example;

import core.annotation.*;
import core.di.aspect.AspectConfig;
import core.di.beans.factory.support.ProxyInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
@Ordered(order = 1)
public class AuthenticationAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationAspect.class);

    public static int BEFORE_COUNT = -1;
    public static int AFTER_COUNT = -1;

    @Inject
    private AuthenticdationService authenticdationService;

    @Around(pointcut = "core.di.aspect.example.AspectUserService*")
    public Object authenticate(ProxyInvocation invocation) {

        if (!authenticdationService.login()) {
            throw new RuntimeException("need login");
        }

        BEFORE_COUNT = AspectConfig.COUNT.incrementAndGet();
        long startTime = System.currentTimeMillis();
        logger.debug("## [Authentication] before invoked");
        final Object result = invocation.proceed();
        logger.debug("## [Authentication] after invoked");
        AFTER_COUNT = AspectConfig.COUNT.incrementAndGet();
        return result;
    }

}
