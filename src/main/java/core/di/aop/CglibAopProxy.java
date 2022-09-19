package core.di.aop;

import java.util.Objects;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibAopProxy implements AopProxy {

    private final Class<?> target;
    private final MethodInterceptor methodInterceptor;

    public CglibAopProxy(final Class<?> target, final PointcutAdvisor advisor) {
        validateNonNull(target);
        validateNonNull(advisor);
        this.target = target;
        this.methodInterceptor = (obj, method, args, proxy) -> {
            if (advisor.matches(method, target)) {
                return advisor.invoke(() -> proxy.invokeSuper(obj, args));
            }
            return proxy.invokeSuper(obj, args);
        };
    }

    private void validateNonNull(final Object target) {
        if (Objects.isNull(target)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Object getProxy() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }

}
