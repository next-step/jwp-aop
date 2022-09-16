package core.aop.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ClassUtils;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.TargetSource;
import core.aop.support.DefaultPointcutAdvisor;
import core.aop.target.SingletonTargetSource;

public class AdvisedSupport implements Advised {

    protected final AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();
    protected TargetSource targetSource;
    private final List<Class<?>> interfaces = new ArrayList<>();
    private final List<Advisor> advisors = new ArrayList<>();
    private boolean proxyTargetClass = false;

    public void setInterfaces(Class<?>... interfaces) {
        for (Class<?> intf : interfaces) {
            if (!this.interfaces.contains(intf)) {
                this.interfaces.add(intf);
            }
        }
    }

    public void setTarget(Object target) {
        this.targetSource = new SingletonTargetSource(target);
    }

    public TargetSource getTargetSource() {
        return this.targetSource;
    }

    public List<Advice> getAdvices(Method method, Class<?> targetClass) {
        return advisorChainFactory.getAdvices(this, method, targetClass);
    }

    @Override
    public Class<?> getTargetClass() {
        return this.targetSource.getTargetClass();
    }

    @Override
    public void addAdvice(Advice advice) {
        addAdvisor(new DefaultPointcutAdvisor(advice));
    }

    @Override
    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    @Override
    public Advisor[] getAdvisors() {
        return this.advisors.toArray(new Advisor[0]);
    }

    @Override
    public boolean isProxyTargetClass() {
        return this.proxyTargetClass;
    }

    @Override
    public Class<?>[] getProxiedInterfaces() {
        return ClassUtils.toClassArray(this.interfaces);
    }
}
