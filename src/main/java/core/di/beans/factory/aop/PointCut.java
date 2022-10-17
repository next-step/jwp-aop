package core.di.beans.factory.aop;

import java.lang.reflect.Method;

public interface PointCut {

    boolean matches(Method method, Class<?> targetClass, Object[] args);
}