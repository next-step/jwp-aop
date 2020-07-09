package core.di.context.support;

import core.di.beans.factory.aop.Advice;
import core.jdbc.TransactionManager;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TransactionAdvice implements Advice {

    @Override
    public Object invoke(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        try {
            TransactionManager.beginTransaction();
            final Object ret = methodProxy.invokeSuper(obj, args);
            TransactionManager.commit();
            return ret;
        } catch (Throwable e) {
            TransactionManager.rollback();
            throw e;
        }
    }
}
