package core.aop;

import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements AopProxy {
    private final Object target;
    private final Advisor advisor;

    public JdkDynamicAopProxy(Object target, Advisor advisor) {
        Assert.notNull(target, "프록시를 위한 타겟 클래스는 null 일 수 없습니다.");
        Assert.notNull(advisor, "프록시를 위한 어드바이저는 null 일 수 없습니다.");
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                getInvocationHandler()
        );
    }

    private InvocationHandler getInvocationHandler() {
        return (proxy, method, args) -> getObject(method, args);
    }

    private Object getObject(Method method, Object[] args) throws Throwable {
        if (this.advisor.matches(method, target.getClass())) {
            return this.advisor.invoke(target, method, args);
        }
        return method.invoke(target, args);
    }
}
