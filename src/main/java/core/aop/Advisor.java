package core.aop;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Callback;

public class Advisor {

    private Advice advice;
    private PointCut pointCut;

    public Advisor(Advice advice, PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }

    public boolean fitPointCut(Method method, Object target, Object[] args) {
        MethodMatcher methodMatcher = this.pointCut.getMethodMatcher();
        return methodMatcher.matches(method, target.getClass(), args);
    }

    public Object advice(Object target, Method method, Object[] args) throws Throwable {


        return this.advice.intercept(target, method, args, null);
    }

    public Advice getAdvice() {
        return this.advice;
    }
}
