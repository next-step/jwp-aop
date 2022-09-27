package core.aop;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

public class CglibAopProxy implements AopProxy {
    private final Object target;
    private final Advisor advisor;

    public CglibAopProxy(Object target, Advisor advisor) {
        Assert.notNull(target, "프록시를 위한 타겟 클래스는 null 일 수 없습니다.");
        Assert.notNull(advisor, "프록시를 위한 어드바이저는 null 일 수 없습니다.");
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(getMethodInterceptor());
        return enhancer.create();
    }

    private MethodInterceptor getMethodInterceptor() {
        return (obj, method, args, proxy) -> getObject(method, args, proxy);
    }

    private Object getObject(Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (this.advisor.matches(method, target.getClass())) {
            return this.advisor.invoke(target, method, args);
        }
        return proxy.invoke(target, args);
    }
}
