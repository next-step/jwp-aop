package study.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private final Object target;
    private final Map<String, Method> methods = new HashMap<>();

    public DynamicInvocationHandler(Object target) {
        this.target = target;
        Method[] declaredMethods = target.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoke method name: {} , args: {}", method.getName(), args[0]);
        Object result = methods.get(method.getName()).invoke(target, args);
        return result.toString().toUpperCase();
    }
}
