package core.aop;

public class SayPointcutAdvisor implements Advisor {

    private final Advice advice;

    public SayPointcutAdvisor(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Advice advice() {
        return this.advice;
    }

    @Override
    public Pointcut pointcut() {
        return SayPointCut.getInstance();
    }
}
