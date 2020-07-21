package study.proxy.jdk;

import lombok.RequiredArgsConstructor;
import core.aop.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UppercaseHandler implements InvocationHandler {

    private final Hello target;
    private final MethodMatcher methodMatcher;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String result = (String) method.invoke(target, args);
        if (methodMatcher.matches(method, target.getClass(), args)) {
            return result.toUpperCase();
        }

        return result;
    }

}
