package core.aop.support;

import core.aop.Advice;
import core.jdbc.DataSourceUtils;
import core.jdbc.TransactionManager;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public class TransactionalAdvice implements Advice {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        TransactionManager.startTransaction();

        return null;
    }
}