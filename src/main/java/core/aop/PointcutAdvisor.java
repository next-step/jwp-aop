package core.aop;

public class PointcutAdvisor extends AbstractAopAdvisor {
    public PointcutAdvisor(Advice advice, Pointcut pointcut) {
        super(advice, pointcut);
    }

    @Override
    public Advice advice() {
        return super.advice();
    }

    @Override
    public Pointcut pointcut() {
        return super.pointcut();
    }
}
