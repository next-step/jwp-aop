package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DataSourceUtils {

    private DataSourceUtils() {
        throw new AssertionError();
    }

    public static Connection getConnection(DataSource dataSource) {
        try {
            return fetchConnection(dataSource);
        } catch (SQLException e) {
            throw new DataAccessException("JDBC Connection 생성 실패", e);
        }
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        final Connection connection = ConnectionHolder.getConnection();
        if (connection == null) {
            ConnectionHolder.setConnection(dataSource.getConnection());
        }

        return ConnectionHolder.getConnection();
    }

    public static void releaseConnection() {
        ConnectionHolder.releaseConnection();
    }
}
