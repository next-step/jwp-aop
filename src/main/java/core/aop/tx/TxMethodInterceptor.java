package core.aop.tx;

import core.aop.pointcut.ClassFilter;
import core.aop.pointcut.MethodMatcher;
import core.aop.pointcut.Pointcut;
import core.di.context.ApplicationContext;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;

@RequiredArgsConstructor
public class TxMethodInterceptor implements MethodInterceptor {

    private final ApplicationContext applicationContext;
    private final Pointcut pointcut;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?> targetClass = obj.getClass();
        if (matchClass(targetClass) || matchMethod(method, args, targetClass)) {
            return applyTransactionProcess(obj, args, proxy);
        }

        return proxy.invokeSuper(obj, args);
    }

    private Object applyTransactionProcess(Object obj, Object[] args, MethodProxy proxy) throws Throwable {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            Object result = proxy.invokeSuper(obj, args);
            connection.commit();

            return result;
        } catch (Exception e) {
            connection.rollback();
            throw new IllegalStateException("Transaction is rollback.", e);
        } finally {
            DataSourceUtils.closeConnection(connection);
        }
    }

    private boolean matchClass(Class<?> targetClass) {
        ClassFilter classFilter = pointcut.getClassFilter();

        return classFilter.matches(targetClass);
    }

    private boolean matchMethod(Method method, Object[] args, Class<?> targetClass) {
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();

        return methodMatcher.matches(method, targetClass, args);
    }

}
