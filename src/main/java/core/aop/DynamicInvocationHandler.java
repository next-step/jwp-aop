package core.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private final Object proxyTarget;
    private final Map<String, Method> methods = new HashMap<>();

    public DynamicInvocationHandler(Object proxyTarget) {
        this.proxyTarget = proxyTarget;
        for (Method method : proxyTarget.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = methods.get(method.getName()).invoke(proxyTarget, args);

        if (method.getName().startsWith("say") && method.getReturnType().equals(String.class)) {
            return invoke.toString().toUpperCase();
        }

        return invoke;
    }
}
