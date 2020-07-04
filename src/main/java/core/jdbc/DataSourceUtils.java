package core.jdbc;

import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {

    public static final ConnectionHolder HOLDER = new ConnectionHolder();

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException, SQLException {
        if (!HOLDER.hasConnection()) {
            HOLDER.setConnection(fetchConnection(dataSource));
        }
        return HOLDER.getConnection();
    }

    public static void releaseConnection() {
        HOLDER.releaseConnection();
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}