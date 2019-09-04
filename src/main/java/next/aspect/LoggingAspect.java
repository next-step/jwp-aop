package next.aspect;

import core.annotation.Around;
import core.annotation.Aspect;
import core.annotation.Component;
import core.aop.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@within(core.annotation.Service)")
    public Object logging(ProceedingJoinPoint pjp) {
        logger.info("before preceed");
        Object result = pjp.proceed();
        logger.info("after proceed");
        return result;
    }


}
