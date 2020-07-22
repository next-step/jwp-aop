package core.aop.tx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) {
        try {
            if (!ConnectionHolder.hasConnection()) {
                ConnectionHolder.setConnection(fetchConnection(dataSource), true);
            }

            return ConnectionHolder.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("fetch connection failed.", e);
        }
    }

    public static void closeConnection(Connection connection) {
        Connection conn = ConnectionHolder.getConnection();

        try {
            ConnectionHolder.clear();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();

        connection.setAutoCommit(false);
        return connection;
    }

}
