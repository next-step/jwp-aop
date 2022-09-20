package core.aop.intercept;

@FunctionalInterface
public interface MethodInterceptor extends Interceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;
}
