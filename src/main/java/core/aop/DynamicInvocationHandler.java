package core.aop;

import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler {

    private final Object target;
    private final MethodMatcher methodMatcher;

    public DynamicInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodMatcher.matches(target, method, args)) {
            return method.invoke(target, args).toString().toUpperCase();
        }
        return method.invoke(target, args).toString();
    }
}
