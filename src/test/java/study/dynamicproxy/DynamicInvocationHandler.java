package study.dynamicproxy;

import study.methodmatcher.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Map<String, Method> methods = new HashMap();
    private final MethodMatcher methodMatcher;

    public DynamicInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        Arrays.stream(target.getClass().getDeclaredMethods())
                .forEach(method -> this.methods.put(method.getName(), method));
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String result = (String) methods.get(method.getName()).invoke(target, args);
        if (methodMatcher.matches(method, target.getClass(), args)) {
            return result.toUpperCase();
        }
        return result;
    }
}
