package core.di.beans.factory.aop.advisor;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class NoOpAdvice implements Advice {
    private static final NoOpAdvice instance = new NoOpAdvice();

    private NoOpAdvice() {}

    public static NoOpAdvice getInstance() {
        return instance;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return proxy.invokeSuper(obj, args);
    }
}
