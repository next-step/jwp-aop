package study.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Map<String, Method> methods = new HashMap<>();

    public DynamicInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String result = (String) methods.get(method.getName()).invoke(target, args);
        return result.toUpperCase();
    }
}
