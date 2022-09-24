package study.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import study.proxy.MethodMatcher;

import java.lang.reflect.Method;

public class HelloUpperCaseMethodInterceptor implements MethodInterceptor {
    private final Object target;
    private final MethodMatcher methodMatcher;

    public HelloUpperCaseMethodInterceptor(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object invokeResult = methodProxy.invoke(target, objects);

        if (invokeResult instanceof String && methodMatcher.matches(method)) {
            return ((String) invokeResult).toUpperCase();
        }

        return invokeResult;
    }
}
