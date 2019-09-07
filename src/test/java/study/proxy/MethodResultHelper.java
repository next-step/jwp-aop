package study.proxy;

import java.lang.reflect.Method;

public class MethodResultHelper {
    public static Object toUpper(Method method, Object result) {
        if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class && result != null) {
            return ((String) result).toUpperCase();
        }
        return result;
    }
}
