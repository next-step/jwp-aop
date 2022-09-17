package core.aop.support;

import core.aop.ClassFilter;
import core.aop.MethodMatcher;
import core.aop.Pointcut;

public abstract class StaticMethodMatcherPointcut extends StaticMethodMatcher implements Pointcut {

    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
