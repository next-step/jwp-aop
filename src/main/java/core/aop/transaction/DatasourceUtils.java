package core.aop.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.CannotGetJdbcConnectionException;

public class DatasourceUtils {

    private DatasourceUtils() {
    }

    public static Connection createConnection(DataSource dataSource) throws
        CannotGetJdbcConnectionException {
        var conHolder = ConnectionHolder.getInstance();

        if (!conHolder.hasConnection()) {
            conHolder.setConnection(fetchConnection(dataSource));
        }
        return conHolder.getConnection();
    }

    public static Connection getConnection(DataSource dataSource) throws
        CannotGetJdbcConnectionException {
        var conHolder = ConnectionHolder.getInstance();

        if (conHolder.hasConnection()) {
            return conHolder.getConnection();
        }
        return fetchConnection(dataSource);
    }

    private static Connection fetchConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new CannotGetJdbcConnectionException(e.getMessage(), e);
        }
    }

    public static void release(Connection connection) {
        var conHolder = ConnectionHolder.getInstance();

        if (conHolder.getConnection() != connection) {
            close(connection);
        }
    }

    public static void releaseProxyConnection(Connection connection) {
        var conHolder = ConnectionHolder.getInstance();

        if (conHolder.getConnection() == connection) {
            close(connection);
            conHolder.clear();
        }
    }

    private static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("커넥션 종료 실패", e);
        }
    }
}
