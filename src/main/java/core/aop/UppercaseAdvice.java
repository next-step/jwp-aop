package core.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UppercaseAdvice implements Advice {

    private static final UppercaseAdvice INSTANCE = new UppercaseAdvice();

    private UppercaseAdvice(){}

    public static UppercaseAdvice getInstance() {
        return INSTANCE;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        try {
            Object invoke = method.invoke(object, args);
            if (invoke.getClass().getName().equals("java.lang.String")) {
                return invoke.toString().toUpperCase();
            }
            return invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
