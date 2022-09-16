package core.aop;

public class TruePointcut implements Pointcut {

    public static final TruePointcut INSTANCE = new TruePointcut();

    private TruePointcut() {
    }

    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return MethodMatcher.TRUE;
    }
}
