package proxy.dynamic;

import proxy.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvocationHandler implements InvocationHandler {

    private Object target;
    private MethodMatcher methodMatcher;

    public HelloInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodMatcher.matches(method, method.getReturnType(), args)) {
            return ((String) method.invoke(target, args)).toUpperCase();
        }

        return method.invoke(target, args);
    }

}
