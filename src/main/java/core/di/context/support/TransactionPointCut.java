package core.di.context.support;

import core.annotation.Transactional;
import core.di.beans.factory.aop.MethodMatcher;
import core.di.beans.factory.aop.PointCut;

public class TransactionPointCut implements PointCut {

    @Override
    public MethodMatcher getMethodMatcher() {
        return (method, targetClass) -> method.isAnnotationPresent(Transactional.class) || targetClass.isAnnotationPresent(Transactional.class);
    }
}
