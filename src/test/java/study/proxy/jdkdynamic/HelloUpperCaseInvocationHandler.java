package study.proxy.jdkdynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelloUpperCaseInvocationHandler implements InvocationHandler {
    private final Object target;
    private final Map<String, Method> methods;

    public HelloUpperCaseInvocationHandler(Object target) {
        this.target = target;
        methods = Arrays.stream(target.getClass().getDeclaredMethods()).collect(Collectors.toMap(Method::getName, Function.identity()));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method matchMethod = methods.get(method.getName());
        Object invokeResult = matchMethod.invoke(target, args);

        if (invokeResult instanceof String) {
            return ((String) invokeResult).toUpperCase();
        }

        return invokeResult;
    }
}
