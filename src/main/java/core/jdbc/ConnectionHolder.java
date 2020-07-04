package core.jdbc;

import java.sql.Connection;

/**
 * Created by yusik on 2020/07/04.
 */
public class ConnectionHolder extends ThreadLocal<Connection> {

    public boolean hasConnection() {
        return get() != null;
    }

    public void setConnection(Connection connection) {
        set(connection);
    }

    public Connection getConnection() {
        return get();
    }

    public void releaseConnection() {
        remove();
    }
}
