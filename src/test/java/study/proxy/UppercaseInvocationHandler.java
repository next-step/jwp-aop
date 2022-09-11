package study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseInvocationHandler implements InvocationHandler {

    private final Object target;

    public UppercaseInvocationHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Object result = method.invoke(target, args);

        return ((String) result).toUpperCase();
    }
}
