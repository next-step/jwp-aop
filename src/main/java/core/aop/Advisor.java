package core.aop;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

public class Advisor {

    private Advice advice;
    private PointCut pointCut;

    public Advisor(Advice advice, PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }

    public Object advice(Object obj, Object[] args, MethodProxy proxy) throws Throwable {
        return advice.advice(obj, args, proxy);
    }

    public boolean fitPointCut(Object target, Object[] args, Method method) {
        boolean isFit = false;

        ClassFilter classFilter = this.pointCut.getClassFilter();
        if (classFilter != null) {
            isFit = classFilter.matches(target.getClass());
        }

        if (isFit) {
            return true;
        }

        MethodMatcher methodMatcher = this.pointCut.getMethodMatcher();
        if (methodMatcher != null) {
            isFit = methodMatcher.matches(method, target.getClass(), args);
        }

        return isFit;
    }

}
