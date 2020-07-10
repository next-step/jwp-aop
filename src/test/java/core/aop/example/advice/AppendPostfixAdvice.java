package core.aop.example.advice;

import core.aop.advice.Advice;
import core.aop.advice.MethodInvocation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppendPostfixAdvice implements Advice {
    public static final String POSTFIX = "postFix";

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("AppendPostfixAdvice - started");
        String result = (String) invocation.proceed();
        log.debug("AppendPostfixAdvice - finished");
        return result + POSTFIX;
    }
}
