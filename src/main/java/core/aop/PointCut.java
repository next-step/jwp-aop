package core.aop;

public interface PointCut {

    MethodMatcher getMethodMatcher();

}

class MethodNameStartsWithPointCut implements PointCut {

    private MethodMatcher methodMatcher;

    public MethodNameStartsWithPointCut(String nameStart) {
        this.methodMatcher = new MethodMatcherUpperCaseStartsWith(nameStart);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.methodMatcher;
    }

}
