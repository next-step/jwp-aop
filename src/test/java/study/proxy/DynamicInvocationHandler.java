package study.proxy;

import com.google.common.collect.Maps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private final Object target;
    private final Map<String, Method> methods = Maps.newHashMap();
    private final MethodMatcher methodMatcher = new SayPrefixMethodMatcher();

    public DynamicInvocationHandler(Object target) {
        this.target = target;
        Arrays.stream(target.getClass().getDeclaredMethods())
                .forEach(it -> this.methods.put(it.getName(), it));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = (String) methods.get(method.getName()).invoke(target, args);

        if (methodMatcher.matches(method, target.getClass(), args)) {
            return result.toUpperCase();
        }

        return result;
    }
}
