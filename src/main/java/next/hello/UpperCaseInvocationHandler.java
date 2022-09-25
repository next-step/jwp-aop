package next.hello;

import com.google.common.collect.Maps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

public class UpperCaseInvocationHandler implements InvocationHandler {
    private final Object target;
    private final Map<String, Method> methods = Maps.newHashMap();

    public UpperCaseInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = methods.get(method.getName()).invoke(target, args);
        if (object instanceof String && method.getName().startsWith("say")) {
            return String.valueOf(object).toUpperCase(Locale.ROOT);
        }
        return object;
    }
}
