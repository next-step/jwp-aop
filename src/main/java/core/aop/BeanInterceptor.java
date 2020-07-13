package core.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public class BeanInterceptor implements MethodInterceptor {

    private Pointcut pointcut;
    private Advice advice;

    public BeanInterceptor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if(!pointcut.matches(method)) {
            return proxy.invokeSuper(obj, args);
        }

        return advice.intercept(obj, method, args, proxy);
    }
}
