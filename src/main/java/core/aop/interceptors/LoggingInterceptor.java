package core.aop.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class LoggingInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info(
            "invoke - start: {}/{}",
            invocation.getThis().getClass().getSimpleName(),
            invocation.getMethod().getName()
        );

        Object proceed = invocation.proceed();

        log.info(
            "invoke - finished: {}/{}",
            invocation.getThis().getClass().getSimpleName(),
            invocation.getMethod().getName()
        );

        return proceed;
    }
}
