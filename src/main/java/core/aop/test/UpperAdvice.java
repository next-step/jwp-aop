package core.aop.test;

import core.aop.Advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UpperAdvice implements Advice {
    @Override
    public Object invoke(Object object, Method method, Object... objects) {

        try {
            return method.invoke(object, objects).toString().toUpperCase();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
