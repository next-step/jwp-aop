package study.proxy.jdk;

import study.proxy.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloHandler implements InvocationHandler {

    private final Object target;
    private final MethodMatcher methodMatcher;

    public HelloHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);

        if (methodMatcher.matches(method, target.getClass(), args)) {
            return ((String) result).toUpperCase();
        }

        return method.invoke(target, args);
    }

}
