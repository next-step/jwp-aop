package core.di.aop;

public interface Advice {

    Object invoke(MethodInvocation methodInvocation) throws Throwable;

}
