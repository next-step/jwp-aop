package core.di.beans.factory.aop;

import java.lang.reflect.Method;

public interface Advice {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}