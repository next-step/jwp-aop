package study.proxy.jdkdynamic;

import study.proxy.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloUpperCaseInvocationHandler implements InvocationHandler {
    private final Object target;
    private final MethodMatcher methodMatcher;

    public HelloUpperCaseInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invokeResult = method.invoke(target, args);

        if (invokeResult instanceof String && methodMatcher.matches(method)) {
            return ((String) invokeResult).toUpperCase();
        }

        return invokeResult;
    }
}
