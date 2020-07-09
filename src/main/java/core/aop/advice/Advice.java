package core.aop.advice;

public interface Advice {
    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
