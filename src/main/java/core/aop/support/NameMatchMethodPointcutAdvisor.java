package core.aop.support;

import core.aop.Advice;
import core.aop.Pointcut;
import core.aop.PointcutAdvisor;

public class NameMatchMethodPointcutAdvisor implements PointcutAdvisor {

    private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    private final Advice advice;

    public NameMatchMethodPointcutAdvisor(Advice advice) {
        this.advice = advice;
    }

    public void setMappedName(String mappedName) {
        this.pointcut.setMappedName(mappedName);
    }

    public void setMappedNames(String... mappedNames) {
        this.pointcut.setMappedNames(mappedNames);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
