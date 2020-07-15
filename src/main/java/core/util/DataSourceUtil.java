package core.util;

import core.jdbc.ConnectionHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public class DataSourceUtil {
    private static final ConnectionHolder CONNECTION_HOLDER = new ConnectionHolder();

    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        if (!CONNECTION_HOLDER.hasConnection()) {
            CONNECTION_HOLDER.setConnection(fetchConnection(dataSource));
        }

        return CONNECTION_HOLDER.getConnection();
    }

    private static Connection fetchConnection(DataSource dataSource) {
        if (Objects.isNull(dataSource)) {
            return null;
        }

        try {
            return dataSource.getConnection();
        }
        catch (SQLException e) {
            log.error("", e);
        }

        return null;
    }

    public static void removeConnection() {
        CONNECTION_HOLDER.removeConnection();
    }
}
