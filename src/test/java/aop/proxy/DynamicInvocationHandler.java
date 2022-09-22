package aop.proxy;

import aop.methodMatcher.MethodMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(DynamicInvocationHandler.class);
    private final MethodMatcher methodMatcher;
    private Object target;
    private final Map<String, Method> methods = new HashMap<>();

    public DynamicInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
        Method[] declaredMethods = target.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            methods.put(declaredMethod.getName(), declaredMethod);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("Invoke method name : {}, args : {}", method.getName(), args[0]);
        Object invoke = methods.get(method.getName()).invoke(target, args);

        if (methodMatcher.matches(method, proxy.getClass(), args)) {
            return invoke.toString().toUpperCase();
        }
        return invoke;
    }
}
