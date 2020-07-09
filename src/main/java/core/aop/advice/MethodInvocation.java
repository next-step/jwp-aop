package core.aop.advice;

public interface MethodInvocation {
    Object proceed() throws Throwable;
}
