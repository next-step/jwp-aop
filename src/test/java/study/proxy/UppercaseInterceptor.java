package study.proxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UppercaseInterceptor implements MethodInterceptor {

    private final Object target;

    public UppercaseInterceptor(final Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        final Object result = proxy.invoke(target, args);

        return ((String) result).toUpperCase();
    }
}
