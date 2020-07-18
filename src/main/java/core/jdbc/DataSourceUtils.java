package core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

public class DataSourceUtils {

    private static final ConnectionHolder CONNECTION_HOLDER = new ConnectionHolder();

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        if (!CONNECTION_HOLDER.hasConnection()) {
            CONNECTION_HOLDER.setConnection(fetchConnection(dataSource));
        }
        return CONNECTION_HOLDER.getConnection();
    }

    private static Connection fetchConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new CannotGetJdbcConnectionException(e.getSQLState(), e);
        }
    }
}
