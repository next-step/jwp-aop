package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proxy.cglib.SayMethodMatcher;

public class DynamicInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private final Object target;
    private final SayMethodMatcher matcher;
    private final Map<String, Method> methods;

    public DynamicInvocationHandler(Object target, SayMethodMatcher matcher) {
        this.matcher = matcher;
        this.target = target;
        methods = Arrays.stream(target.getClass().getDeclaredMethods())
            .collect(Collectors.toMap(method -> method.getName(), method -> method));
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
