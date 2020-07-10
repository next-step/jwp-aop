package core.aop.advice;

public interface Advice {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
