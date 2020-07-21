package core.aop.tx;

import core.aop.ClassFilter;
import core.aop.MethodMatcher;
import core.aop.pointcut.Pointcut;

public class TxPointcut implements Pointcut {

    private ClassFilter classFilter = new TxClassFilter();
    private MethodMatcher methodMatcher = new TxMethodMatcher();

    @Override
    public ClassFilter getClassFilter() {
        return classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

}
