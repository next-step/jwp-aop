package study.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.cglib.SayMethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private final Object target;
    private final SayMethodMatcher matcher;
    private final Map<String, Method> methods = new HashMap<>();

    public DynamicInvocationHandler(Object target, SayMethodMatcher matcher) {
        this.matcher = matcher;
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
        if (matcher.matches(method, proxy.getClass(), args)) {
            return result.toString().toUpperCase();
        }
        return result;
    }
}
