package core.aop.framework;

import core.aop.TargetClassAware;

public interface Advised extends TargetClassAware {

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();
}
