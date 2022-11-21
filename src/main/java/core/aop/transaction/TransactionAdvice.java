package core.aop.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import core.di.beans.factory.proxy.Advice;
import core.di.beans.factory.proxy.JoinPoint;

public class TransactionAdvice implements Advice {

    private final DataSource dataSource;

    public TransactionAdvice(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(JoinPoint joinPoint) {
        Connection connection = DatasourceUtils.createConnection(dataSource);
        try {
            connection.setAutoCommit(false);
            var result = joinPoint.proceed();
            connection.commit();

            return result;
        } catch (Exception e) {
            rollback(connection);

            throw new IllegalStateException("트랜잭션 실패", e);
        } finally {
            DatasourceUtils.releaseProxyConnection(connection);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new IllegalStateException("롤백 실패", e);
        }
    }

    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("커넥션 close 실패", e);
        }
    }
}
