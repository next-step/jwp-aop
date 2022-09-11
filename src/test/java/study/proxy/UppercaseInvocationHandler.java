package study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseInvocationHandler implements InvocationHandler {

    private final Object target;
    private final SayMethodMatcher sayMethodMatcher;

    public UppercaseInvocationHandler(final Object target) {
        this.target = target;
        this.sayMethodMatcher = new DefaultSayMethodMatcher();
    }

    public UppercaseInvocationHandler(final Object target, final SayMethodMatcher sayMethodMatcher) {
        this.target = target;
        this.sayMethodMatcher = sayMethodMatcher;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Object result = method.invoke(target, args);

        if (sayMethodMatcher.matches(method, target.getClass(), args)) {
            return ((String) result).toUpperCase();
        }

        return result;
    }

}
