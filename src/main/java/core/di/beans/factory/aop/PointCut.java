package core.di.beans.factory.aop;

/**
 * Pointcut specifies where exactly to apply advice.
 */
public interface PointCut {
    MethodMatcher getMethodMatcher();
}
