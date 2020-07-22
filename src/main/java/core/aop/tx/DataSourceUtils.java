package core.aop.tx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) {
        return getConnection(dataSource, false);
    }

    public static Connection getConnection(DataSource dataSource, boolean maintained) {
        try {
            if (!ConnectionHolder.hasConnection()) {
                Connection connection = fetchConnection(dataSource);
                ConnectionHolder.setConnection(connection, maintained);
            }

            return ConnectionHolder.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("fetch connection failed.", e);
        }
    }

    public static void closeConnection() {
        try {
            ConnectionHolder.clear();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void closeConnectionCompletely() {
        try {
            ConnectionHolder.clearCompletely();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

}
