package core.util;

import core.jdbc.ConnectionHolder;
import core.jdbc.DefaultConnectionHolder;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public abstract class DataSourceUtils {
    private static final ThreadLocal<ConnectionHolder> connectionHolders = new InheritableThreadLocal<>();

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException, SQLException {
        ConnectionHolder connHolder = connectionHolders.get();
        if (Objects.isNull(connHolder)) {
            connHolder = new DefaultConnectionHolder(fetchConnection(dataSource));
            connectionHolders.set(connHolder);
        }

        if (!connHolder.hasConnection()) {
            connHolder.setConnection(fetchConnection(dataSource));
        }

        return connHolder.getConnection()  ;
    }

    private static Connection fetchConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    public static void invalidate() throws SQLException {
        connectionHolders.get().getConnection().close();
        connectionHolders.remove();
    }
}
