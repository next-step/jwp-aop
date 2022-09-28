package core.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UpperCaseAdvice implements Advice {
    private static final UpperCaseAdvice INSTANCE = new UpperCaseAdvice();

    public static UpperCaseAdvice getInstance() {
        return INSTANCE;
    }

    private UpperCaseAdvice() {
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        try {
            Object invokeResult = method.invoke(object, args);

            if (invokeResult instanceof String) {
                return ((String) invokeResult).toUpperCase();
            }
            return invokeResult;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
