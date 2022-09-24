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
            return method.invoke(object, args).toString().toUpperCase();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
