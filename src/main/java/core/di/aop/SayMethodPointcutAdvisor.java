package core.di.aop;

public class SayMethodPointcutAdvisor implements Advisor {

    private final Advice advice;

    public SayMethodPointcutAdvisor(final Advice advice) {
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return SayMethodPointcut.getInstance();
    }
}
