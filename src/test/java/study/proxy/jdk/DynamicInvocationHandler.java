package study.proxy.jdk;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private Object target;
    private final Map<String, Method> methods = Maps.newHashMap();

    public DynamicInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoke method name: {}, args: {}", method.getName(), args[0]);
        Object invokeResult = methods.get(method.getName()).invoke(target, args);

        if (invokeResult instanceof String) {
            return String.valueOf(invokeResult).toUpperCase();
        }

        return invokeResult;
    }
}
