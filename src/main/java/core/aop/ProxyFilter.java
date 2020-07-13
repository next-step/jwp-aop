package core.aop;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public class ProxyFilter implements CallbackFilter {

    private Pointcut pointcut;

    public ProxyFilter(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public int accept(Method method) {
        return pointcut.matches(method) ? 0 : 1;
    }
}
