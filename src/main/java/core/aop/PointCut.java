package core.aop;

import java.lang.reflect.Method;

public interface PointCut {
    boolean matches(Method method, Class<?> targetClass);
}
