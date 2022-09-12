package study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseInvocationHandler implements InvocationHandler {

    private final Object target;
    private final MethodMatcher methodMatcher;

    public UppercaseInvocationHandler(final Object target) {
        this.target = target;
        this.methodMatcher = new SayMethodMatcher();
    }

    public UppercaseInvocationHandler(final Object target, final MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Object result = method.invoke(target, args);

        if (methodMatcher.matches(method, target.getClass(), args)) {
            return ((String) result).toUpperCase();
        }

        return result;
    }

}
