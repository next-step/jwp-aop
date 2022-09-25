package core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CGLibAopProxy implements AopProxy {

    private final Object target;
    private final MethodInterceptor methodInterceptor;

    public CGLibAopProxy(Object target, AbstractAopAdvisor advisor) {
        this.target = target;
        this.methodInterceptor = (obj, method, args, proxy) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(target, method, args);
            }
            return String.valueOf(proxy.invoke(target, args));
        };
    }

    @Override
    public Object proxy() {
        return Enhancer.create(target.getClass(), methodInterceptor);
    }
}
