package core.mvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodExecutor {
    private Object bean;
    private Method method;

    public MethodExecutor(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    Object execute(Object... args) {
        try {
            return method.invoke(bean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MethodInvocationException(e);
        }
    }
}
