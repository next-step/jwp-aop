package core.aop.tx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DataSourceUtils {

    private static final ConnectionHolder CONNECTION_HOLDER = ConnectionHolder.create();

    public static Connection getConnection(DataSource dataSource) {
        try {
            if (!CONNECTION_HOLDER.hasConnection()) {
                CONNECTION_HOLDER.setConnection(fetchConnection(dataSource));
            }

            return ConnectionHolder.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("fetch connection failed.", e);
        }
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();

        connection.setAutoCommit(false);
        return connection;
    }

}
