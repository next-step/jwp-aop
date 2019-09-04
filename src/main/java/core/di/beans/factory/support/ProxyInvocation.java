package core.di.beans.factory.support;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyInvocation {

    private Object obj;
    private Method method;
    private Object[] args;
    private MethodProxy proxy;

    public ProxyInvocation(Object obj, Method method, Object[] args, MethodProxy proxy) {
        this.obj = obj;
        this.method = method;
        this.args = args;
        this.proxy = proxy;
    }

    public Object proceed() {
        try {
            return proxy.invokeSuper(obj, args);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

}
