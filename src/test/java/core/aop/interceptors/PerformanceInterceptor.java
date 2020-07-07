package core.aop.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;

@Slf4j
public class PerformanceInterceptor implements MethodInterceptor {
    private StopWatch stopWatch = new StopWatch();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        stopWatch.start();
        Object proceed = invocation.proceed();
        stopWatch.stop();

        log.info("elapsed: {}", stopWatch.prettyPrint());
        return proceed;
    }
}
