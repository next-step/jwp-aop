package study.dynamicproxy;

import study.matcher.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private Object target;
    private final Map<String, Method> methods = new HashMap();
    private MethodMatcher matcher;

    public DynamicInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
        this.matcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke method name : " + method.getName() + ", args : " + args[0]);

        Object result = methods.get(method.getName()).invoke(target, args);
        if (result instanceof String
                && matcher.matches(method, method.getClass(), args)) {
            return ((String) result).toUpperCase();
        }

        return result;
    }
}
