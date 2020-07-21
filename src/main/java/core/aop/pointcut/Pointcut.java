package core.aop.pointcut;

import core.aop.ClassFilter;
import core.aop.MethodMatcher;

public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
