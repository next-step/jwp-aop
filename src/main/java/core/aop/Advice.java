package core.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public interface Advice {
    Object doAdvice(Object obj, Method method, Object[] args, MethodProxy proxy);
}
