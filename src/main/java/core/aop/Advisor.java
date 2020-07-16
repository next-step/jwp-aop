package core.aop;


public class Advisor {

    public static final Advisor NOTHING = new Advisor(Pointcut.MATCH, Advice.NOTHING);

    private final Pointcut pointcut;
    private final Advice advice;

    public Advisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    public Advice getAdvice() {
        return advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }
}
