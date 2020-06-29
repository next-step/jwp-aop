package study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloUppercaseInvocationHandler implements InvocationHandler {
    private final Object target; //실행의 타겟
    private final MethodMatcher methodMatcher;

    public HelloUppercaseInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        Object ret = method.invoke(target, arguments);

        if (!(ret instanceof String) || !methodMatcher.matches(method, target.getClass(), arguments)) {
            return ret;
        }

        return ((String) ret).toUpperCase();
    }
}
