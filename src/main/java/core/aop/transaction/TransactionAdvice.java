package core.aop.transaction;

import core.aop.Advice;
import core.aop.MethodInvocation;
import core.jdbc.ConnectionHolder;
import java.sql.Connection;

class TransactionAdvice implements Advice {

    @Override
    public Object intercept(MethodInvocation invocation) throws Throwable {
        try {
            Object retVal = invocation.proceed();

            Connection connection = ConnectionHolder.getConnection();
            if(connection != null){
                connection.commit();
            }
            return retVal;
        } catch (Exception e){
            ConnectionHolder.getConnection().rollback();
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            ConnectionHolder.getConnection().close();
        }
    }
}
