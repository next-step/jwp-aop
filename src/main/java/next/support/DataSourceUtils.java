package next.support;

import core.jdbc.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

public abstract class DataSourceUtils {

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        return ConnectionHolder.getConnection(dataSource);
//        if (!ConnectionHolder.hasConnection()) {
//            ConnectionHolder.setConnection(fetchConnection(dataSource));
//        }
//
//        Connection connection = ConnectionHolder.getConnection();
//        return connection;
    }

    private static Connection fetchConnection(DataSource dataSource) {
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            throw new CannotGetJdbcConnectionException("Failed to obtain JDBC Connection", ex);
        }
    }

    public static void close() throws DataAccessException {
        ConnectionHolder.close();
    }

}
