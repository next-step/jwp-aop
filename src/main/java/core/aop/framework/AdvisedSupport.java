package core.aop.framework;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ClassUtils;

import core.aop.TargetSource;
import core.aop.target.SingletonTargetSource;

public class AdvisedSupport implements Advised {

    protected TargetSource targetSource;
    private final List<Class<?>> interfaces = new ArrayList<>();
    private boolean proxyTargetClass = false;

    public void setTarget(Object target) {
        this.targetSource = new SingletonTargetSource(target);
    }

    public TargetSource getTargetSource() {
        return this.targetSource;
    }

    @Override
    public Class<?> getTargetClass() {
        return this.targetSource.getTargetClass();
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
