package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CGLIBProxy implements Proxy {

    private final Object target;
    private final MethodInterceptor methodInterceptor;

    public CGLIBProxy(Object target, Advisor advisor) {
        this.target = target;
        this.methodInterceptor = ((obj, method, args, proxy) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(target, method, args);
            }

            return proxy.invoke(target, args);
        });
    }

    @Override
    public Object proxy() {
        return Enhancer.create(target.getClass(), methodInterceptor);
    }
}
