package study.proxy.jdk;

import study.proxy.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler, MethodMatcher {

    private final Object target;

    public DynamicInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

        Object returnValue = method.invoke(target, args);
        if (matches(method, target.getClass(), args)) {
            return ((String) returnValue).toUpperCase();
        }

        return returnValue;
    }

    @Override
    public boolean matches(Method method, Class target, Object[] args) {
        return method.getName().startsWith("say") && String.class == method.getReturnType();
    }
}