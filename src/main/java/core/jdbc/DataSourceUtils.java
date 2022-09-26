package core.jdbc;

import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        return getConnection(dataSource, false);
    }

    public static Connection getConnection(DataSource dataSource, boolean transactionActive)  {
        if (!ConnectionHolder.hasConnection()) {
            try {
                ConnectionHolder.setConnection(dataSource.getConnection(), transactionActive);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ConnectionHolder.getConnection();
    }

    public static void releaseTransactionalConnection() {
        try {
            ConnectionHolder.releaseTransactionalConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void releaseConnection() {
        ConnectionHolder.releaseConnection();
    }
}
