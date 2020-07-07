package core.aop.pointcut;

import core.aop.PointCut;

import java.lang.reflect.Method;

public class BypassPointCut implements PointCut {
    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] arguments) {
        return true;
    }
}
