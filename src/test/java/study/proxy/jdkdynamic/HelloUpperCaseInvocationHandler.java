package study.proxy.jdkdynamic;

import study.proxy.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelloUpperCaseInvocationHandler implements InvocationHandler {
    private final Object target;
    private final MethodMatcher methodMatcher;
    private final Map<String, Method> methods;

    public HelloUpperCaseInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
        methods = Arrays.stream(target.getClass().getDeclaredMethods()).collect(Collectors.toMap(Method::getName, Function.identity()));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method matchedMethod = methods.get(method.getName());
        Object invokeResult = matchedMethod.invoke(target, args);

        if (invokeResult instanceof String && methodMatcher.matches(method, target.getClass(), args)) {
            return ((String) invokeResult).toUpperCase();
        }

        return invokeResult;
    }
}
