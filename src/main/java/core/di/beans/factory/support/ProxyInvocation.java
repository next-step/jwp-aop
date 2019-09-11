package core.di.beans.factory.support;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyInvocation {

    private Object obj;
    private Method method;
    private Object[] args;
    private MethodProxy proxy;
    private AspectInvocationChain chain;

    public ProxyInvocation(Object obj, Method method, Object[] args, MethodProxy proxy) {
        this.obj = obj;
        this.method = method;
        this.args = args;
        this.proxy = proxy;
    }

    public Object proceed() {
        try {
            if (chain != null) {
                chain.invoke(this);
            }
            return proxy.invokeSuper(obj, args);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public void setChain(AspectInvocationChain chain) {
        this.chain = chain;
    }

    public Object getObj() {
        return obj;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public MethodProxy getProxy() {
        return proxy;
    }
}
