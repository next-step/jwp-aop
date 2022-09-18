package core.aop;



import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private Object target;
    private final Map<String, Method> methods = new HashMap<>();
    private MethodMatcher methodMatcher;

    public DynamicInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }

        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = methods.get(method.getName()).invoke(target, args);

        if (methodMatcher.matches(method, proxy.getClass(), args)) {
            return result.toString().toUpperCase();
        }

        return result;
    }
}
