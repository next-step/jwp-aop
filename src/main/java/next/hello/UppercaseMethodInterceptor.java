package next.hello;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Locale;

public class UppercaseMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);
        if (returnValue instanceof String && method.getName().startsWith("say")) {
            return String.valueOf(returnValue).toUpperCase(Locale.ROOT);
        }
        return returnValue;
    }
}
