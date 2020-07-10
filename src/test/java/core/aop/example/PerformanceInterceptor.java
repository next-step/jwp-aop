package core.aop.example;

import core.aop.advice.Advice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

@Slf4j
public class PerformanceInterceptor implements Advice {
    private StopWatch stopWatch = new StopWatch();

    @Override
    public Object invoke(core.aop.advice.MethodInvocation invocation) throws Throwable {
        stopWatch.start();
        Object proceed = invocation.proceed();
        stopWatch.stop();

        log.info("elapsed: {}", stopWatch.prettyPrint());
        return proceed;
    }
}
