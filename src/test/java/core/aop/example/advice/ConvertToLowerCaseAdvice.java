package core.aop.example.advice;

import core.aop.advice.Advice;
import core.aop.advice.MethodInvocation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvertToLowerCaseAdvice implements Advice {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("toLowerCaseAdvice - started");
        String result = (String) invocation.proceed();
        log.debug("toLowerCaseAdvice - finished");
        return result.toLowerCase();
    }
}
