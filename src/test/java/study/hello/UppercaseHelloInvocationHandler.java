package study.hello;

import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class UppercaseHelloInvocationHandler implements InvocationHandler {

    private final Hello target;
    private final Map<String, Method> methods;
    private final MethodMatcher methodMatcher;

    UppercaseHelloInvocationHandler(Hello target, MethodMatcher methodMatcher) {
        Assert.notNull(target, "'target' must not be null");
        Assert.notNull(methodMatcher, "'methodMatcher' must not be null");
        this.target = target;
        this.methodMatcher = methodMatcher;
        this.methods = Stream.of(target.getClass().getDeclaredMethods())
                .collect(Collectors.toUnmodifiableMap(Method::getName, method -> method));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = methods.get(method.getName()).invoke(target, args);
        if (methodMatcher.matches(method, target.getClass(), args)) {
            return result.toString().toUpperCase();
        }
        return result;
    }
}
