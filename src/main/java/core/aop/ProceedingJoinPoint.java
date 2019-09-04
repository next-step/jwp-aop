package core.aop;

import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ProceedingJoinPoint {

    private static final Logger logger = LoggerFactory.getLogger(ProceedingJoinPoint.class);

    private Object obj;
    private Method method;
    private Object[] args;
    private MethodProxy methodProxy;

    public ProceedingJoinPoint(Object obj, Method method, Object[] args, MethodProxy methodProxy) {
        this.obj = obj;
        this.method = method;
        this.args = args;
        this.methodProxy = methodProxy;
    }

    public Object proceed() {
        try {
            return methodProxy.invokeSuper(obj, args);
        } catch (Throwable throwable) {
            throw new RuntimeException(methodProxy.getSuperName() + " proceed fail", throwable);
        }
    }
}
