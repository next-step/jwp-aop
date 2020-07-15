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
    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        ConnectionHolder conHolder = new ConnectionHolder();

        if (!conHolder.hasConnection()) {
            conHolder.setConnection(fetchConnection(dataSource));
        }

        return conHolder.getConnection();
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
}
