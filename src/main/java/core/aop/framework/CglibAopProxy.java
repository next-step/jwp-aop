package core.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibAopProxy implements AopProxy {

    private final Object target;

    public CglibAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback((MethodInterceptor)(obj, method, args, proxy) -> {
            String result = String.valueOf(proxy.invoke(target, args));
            String methodName = method.getName();
            if (methodName.startsWith("say")) {
                return result.toUpperCase();
            }
            return result;
        });
        return enhancer.create();
    }
}
