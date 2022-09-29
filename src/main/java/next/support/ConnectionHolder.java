package next.support;

import core.jdbc.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConnectionHolder {

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private ConnectionHolder() { }

    public static boolean hasConnection() {
        return CONNECTION_THREAD_LOCAL.get() != null;
    }

    public static void setConnection(Connection connection) {
        CONNECTION_THREAD_LOCAL.remove();
        CONNECTION_THREAD_LOCAL.set(connection);
    }

    public static Connection getConnection() {
        return CONNECTION_THREAD_LOCAL.get();
    }

    public static Connection getConnection(DataSource dataSource) {
        if (!hasConnection()) {
            try {
                CONNECTION_THREAD_LOCAL.set(dataSource.getConnection());
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        }
        return CONNECTION_THREAD_LOCAL.get();
    }

    public static void close() throws DataAccessException {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            CONNECTION_THREAD_LOCAL.remove();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

    }

}
