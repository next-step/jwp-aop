package core.di.beans.factory.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface Advice {

    /**
     * method interceptor
     *
     * @param obj target
     * @param method method to invoke
     * @param args parameter
     * @param methodProxy proxy
     * @return result
     */
    Object invoke(Object obj, Method method, Object[] args, MethodProxy methodProxy);
}
