package core.aop.tx;

import core.aop.ClassFilter;
import core.aop.MethodMatcher;
import core.aop.pointcut.Pointcut;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

@RequiredArgsConstructor
public class TxInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Pointcut pointcut;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> targetClass = target.getClass();
        if (matchClass(targetClass) || matchMethod(method, args, targetClass)) {
            Connection connection = ConnectionHolder.getConnection();

            try {
                Object result = method.invoke(target, args);
                connection.commit();

                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new IllegalStateException("Transaction is rollback.", e);
            } finally {
                connection.close();
            }
        }

        return method.invoke(target, args);
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
