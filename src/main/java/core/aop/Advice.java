package core.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public interface Advice extends MethodInterceptor {
    Advice DEFAULT_ADVICE = (obj, method, args, proxy) -> proxy.invokeSuper(obj, args);
}
