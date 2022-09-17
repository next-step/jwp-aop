package core.aop.framework;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.TargetClassAware;

public interface Advised extends TargetClassAware {

    boolean isProxyTargetClass();

    void addAdvice(Advice advice);

    Advisor[] getAdvisors();

    void addAdvisor(Advisor advisor);

    Class<?>[] getProxiedInterfaces();

}
