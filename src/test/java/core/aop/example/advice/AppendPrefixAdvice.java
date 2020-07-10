package core.aop.example.advice;

import core.aop.advice.Advice;
import core.aop.advice.MethodInvocation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppendPrefixAdvice implements Advice {
    public static final String PREFIX = "prefix";

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("AppendPrefixAdvice - started");
        String result = (String) invocation.proceed();
        log.debug("AppendPrefixAdvice - finished");
        return PREFIX + result.toLowerCase();
    }
}
