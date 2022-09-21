package core.aop;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public interface Advice extends MethodInterceptor {

}

class UpperCaseAdvice implements Advice {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String result = (String) proxy.invokeSuper(obj, args);

        return result.toUpperCase();
    }

}
