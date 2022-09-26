package next.handler;

import com.google.common.collect.Maps;
import next.matcher.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

public class UpperCaseInvocationHandler implements InvocationHandler {
    private final Object target;
    private final Map<String, Method> methods = Maps.newHashMap();
    private final MethodMatcher methodMatcher;

    public UpperCaseInvocationHandler(Object target) {
        this(target, null);
    }

    public UpperCaseInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = methods.get(method.getName()).invoke(target, args);
        if (object instanceof String && isMatch(proxy, method, args)) {
            return String.valueOf(object).toUpperCase(Locale.ROOT);
        }
        return object;
    }

    private boolean isMatch(Object proxy, Method method, Object[] args) {
        if (methodMatcher == null) {
            return true;
        }
        return methodMatcher.matches(method, proxy.getClass(), args);
    }
}
