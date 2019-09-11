package study.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelloHandler implements InvocationHandler {

    private final Object instance;
    private final Map<String, Method> methods;

    public HelloHandler(Object instance) {
        this.instance = instance;
        methods = Arrays.stream(instance.getClass().getMethods())
                        .collect(Collectors.toMap(Method::getName, Function.identity(), (before, after) -> after));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = (String) methods.get(method.getName()).invoke(instance, args);
        return result.toUpperCase();
    }

}
