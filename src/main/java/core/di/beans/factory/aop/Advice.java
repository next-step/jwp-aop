package core.di.beans.factory.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public interface Advice {

    Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable;
}