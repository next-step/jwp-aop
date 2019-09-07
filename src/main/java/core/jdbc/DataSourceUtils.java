package core.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) throws SQLException {
        final Connection connection = ConnectionHolder.get();

        if (connection != null) {
            return connection;
        }

        return dataSource.getConnection();
    }

    public static void connectionClear() {
        ConnectionHolder.clear();
    }

    public static void closeConnection(Connection conn) {
        final Connection connection = ConnectionHolder.get();

        if (connection == conn) {
            return;
        }

        try {
            conn.close();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
