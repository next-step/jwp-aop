package study.proxy.factorybean;

import core.di.beans.factory.aop.advisor.Advice;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ToUpperCaseAdvice implements Advice {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String result = (String) proxy.invokeSuper(obj, args);

        return result.toUpperCase();
    }
}
