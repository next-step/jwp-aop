package hello;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloUppercaseInvocationHandlerWithSay implements InvocationHandler {
    private final Hello target;
    private final Map<String, Method> methods;

    public HelloUppercaseInvocationHandlerWithSay(Hello target) {
        this.target = target;
        this.methods = Stream.of(target.getClass().getDeclaredMethods())
                .collect(Collectors.toUnmodifiableMap(Method::getName, method -> method));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = methods.get(method.getName()).invoke(target, args);

        if (StringUtils.startsWith(method.getName(), "say")) {
            return result.toString().toUpperCase();
        }

        return result.toString();
    }
}
