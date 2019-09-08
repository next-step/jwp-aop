package study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.proxy.MethodPredicate;
import study.proxy.MethodResultHelper;

import java.lang.reflect.Method;

public class ToUpperMethodInterceptor implements MethodInterceptor {

    private MethodPredicate methodPredicate;

    public void setMethodPredicate(MethodPredicate methodPredicate) {
        this.methodPredicate = methodPredicate;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = proxy.invokeSuper(obj, args);
        if (methodPredicate != null && !methodPredicate.matches(method, args)) {
            return result;
        }
        return MethodResultHelper.toUpper(method, result);
    }
}
