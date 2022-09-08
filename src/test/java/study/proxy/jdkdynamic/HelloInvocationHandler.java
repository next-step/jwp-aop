package study.proxy.jdkdynamic;

import study.proxy.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvocationHandler implements InvocationHandler {

    private final Object target;
    private final MethodMatcher methodMatcher;

    public HelloInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!methodMatcher.matches(method, target.getClass(), args)) {
            return method.invoke(target, args);
        }
        String result = String.valueOf(method.invoke(target, args)).toUpperCase();
        return result.toUpperCase();
    }
}
