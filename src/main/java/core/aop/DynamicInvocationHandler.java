package core.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private final Object proxyTarget;
    private final Map<String, Method> methods = new HashMap<>();

    private MethodMatcher methodMatcher;

    public DynamicInvocationHandler(Object proxyTarget) {
        this.proxyTarget = proxyTarget;
        for (Method method : proxyTarget.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    public DynamicInvocationHandler(Object proxyTarget, MethodMatcher methodMatcher) {
        this(proxyTarget);
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = methods.get(method.getName()).invoke(proxyTarget, args);

        if (methodMatcher.matches(method, proxyTarget.getClass(), args) && method.getReturnType().equals(String.class)) {
            return invoke.toString().toUpperCase();
        }

        return invoke;
    }
}
