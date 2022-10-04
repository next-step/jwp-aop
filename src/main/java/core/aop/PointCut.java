package core.aop;

public interface PointCut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

    class TransactionalPointCut implements PointCut {

        private ClassFilter classFilter = new TransactionalClassFilter();

        @Override
        public ClassFilter getClassFilter() {
            return this.classFilter;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return null;
        }

    }

    class MethodNameStartsWithPointCut implements PointCut {

        private MethodMatcher methodMatcher;

        public MethodNameStartsWithPointCut(String nameStart) {
            this.methodMatcher = new MethodMatcherUpperCaseStartsWith(nameStart);
        }

        @Override
        public ClassFilter getClassFilter() {
            return null;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return this.methodMatcher;
        }

    }
}

