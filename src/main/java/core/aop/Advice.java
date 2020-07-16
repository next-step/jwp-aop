package core.aop;

public interface Advice {
    Advice NOTHING = invocation -> invocation.proceed();

    Object intercept(MethodInvocation invocation) throws Throwable;
}
