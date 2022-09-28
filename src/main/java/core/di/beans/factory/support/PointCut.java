package core.di.beans.factory.support;

import java.lang.reflect.Method;

public interface PointCut {
    boolean matches(Method method, Class<?> clazz, Object[] args);
}
