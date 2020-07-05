package core.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public interface Advice {
    Object doAdvice(Object object, Method method, Object[] arguments, MethodProxy proxy);
}
