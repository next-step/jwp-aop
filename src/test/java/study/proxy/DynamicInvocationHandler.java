package study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler {

    private final Object target;

    public DynamicInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (!supportsParameter(args)) {
            throw new UnsupportedOperationException();
        }

        String result = (String) method.invoke(target, args);
        return result.toUpperCase();
    }

    private boolean supportsParameter(Object[] args) {
        for (Object arg : args) {
            if (String.class != arg.getClass()) {
                return false;
            }
        }
        return true;
    }
}