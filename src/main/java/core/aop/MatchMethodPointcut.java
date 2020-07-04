package core.aop;

/**
 * Created by yusik on 2020/07/03.
 */
public class MatchMethodPointcut implements Pointcut {

    private final MethodMatcher methodMatcher;

    public MatchMethodPointcut(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public ClassFilter getClassFilter() {
        return clazz -> true;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
