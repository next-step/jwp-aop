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
            ConnectionHolder connectionHolder = ConnectionHolder.create();
            if (!connectionHolder.hasConnection()) {
                connectionHolder.setConnection(fetchConnection(dataSource));
            }

            return connectionHolder.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("fetch connection failed.", e);
        }
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        if (connection == null) {
            throw new IllegalStateException("fetch connection failed.");
        }

        return connection;
    }

}
