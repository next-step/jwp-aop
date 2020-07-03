package core.aop;


/**
 * Created by yusik on 2020/07/03.
 */
public interface Pointcut {

    Pointcut DEFAULT = new Pointcut() {
        @Override
        public ClassFilter getClassFilter() {
            return clazz -> true;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return ((method, targetClass) -> true);
        }
    };

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
