package study.proxy;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private Object target;
    private MethodMatcher methodMatcher;
    private final Map<String, Method> methods = Maps.newHashMap();

    public DynamicInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
        addMethods(target);
    }

    private void addMethods(Object target) {
        for (Method method : target.getClass().getDeclaredMethods()) {
            methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Invoke method name: {}, args: {}", method.getName(), args);

        Object result = methods.get(method.getName()).invoke(target, args);

        if (methodMatcher.matches(method, target.getClass(), args)) {
            result = ((String) result).toUpperCase();
        }

        return result;
    }
}
