package study.dynamicProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicProxyHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DynamicProxyHandler.class);

    private MethodMatcher methodMatcher;
    private Hello target;
    private final Map<String, Method> methods = new HashMap();

    public DynamicProxyHandler(Hello hello) {
        this(hello,null);
    }

    public DynamicProxyHandler(Hello target, MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
        this.target = target;
        initMethods();
    }

    private void initMethods() {
        Arrays.stream(target.getClass().getDeclaredMethods())
                .forEach(method -> methods.put(method.getName(), method));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = methods.get(method.getName()).invoke(target, args);
        if (methodMatcher != null && methodMatcher.matches(method, proxy.getClass(), args)) {
            return result.toString().toUpperCase();
        }
        return result;
    }

}
