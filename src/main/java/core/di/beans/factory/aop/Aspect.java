package core.di.beans.factory.aop;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

public abstract class Aspect {

    final PointCut pointCut;

    public Aspect(PointCut pointCut) {
        Assert.notNull(pointCut, "pointCut must not null");
        this.pointCut = pointCut;
    }
    boolean matches(Method method, Class<?> targetClass, Object[] args) {
        return pointCut.matches(method, targetClass, args);
    }
}