package aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(DynamicInvocationHandler.class);
    private Object target;
    private final Map<String, Method> methods = new HashMap<>();

    public DynamicInvocationHandler(Object target) {
        this.target = target;

        Method[] declaredMethods = target.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            methods.put(declaredMethod.getName(), declaredMethod);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("method name : {} arg {}", args[0]);
        Object invoke = methods.get(method.getName()).invoke(target, args);

        return invoke.toString().toUpperCase();
    }
}
