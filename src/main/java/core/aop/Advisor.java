package core.aop;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

public class Advisor {
    private Advice advice;
    private PointCut pointCut;

    public Advisor(Advice advice, PointCut pointCut) {
        Assert.notNull(advice, "Advice 는 null 일 수 없습니다.");
        Assert.notNull(pointCut, "PointCut 는 null 일 수 없습니다.");
        this.advice = advice;
        this.pointCut = pointCut;
    }

    public Object invoke(Object object, Method method, Object[] args) {
        return this.advice.invoke(object, method, args);
    }

    public boolean matches(Method method, Class<?> targetClass) {
        return this.pointCut.matches(method, targetClass);
    }
}
