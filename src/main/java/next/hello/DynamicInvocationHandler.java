package next.hello;

import com.google.common.collect.Maps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private final Object target;
    private final Map<String, Method> methods = Maps.newHashMap();

    public DynamicInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = methods.get(method.getName()).invoke(target, args);
        if (object instanceof String) {
            return String.valueOf(object).toUpperCase(Locale.ROOT);
        }
        return object;
    }
}
