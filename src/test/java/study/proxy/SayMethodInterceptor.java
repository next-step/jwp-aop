package study.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author : yusik
 * @date : 01/09/2019
 */
public class SayMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String result = (String) proxy.invokeSuper(obj, args);
        return result.toUpperCase();
    }
}
