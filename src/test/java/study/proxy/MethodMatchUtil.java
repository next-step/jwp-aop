package study.proxy;

import java.lang.reflect.Method;

public class MethodMatchUtil {
    public static final String TARGET_METHOD_NAME_PREFIX = "say";

    public static boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getReturnType().equals(String.class) && method.getName().startsWith(TARGET_METHOD_NAME_PREFIX);
    }
}
