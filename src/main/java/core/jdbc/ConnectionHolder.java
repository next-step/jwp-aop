package core.jdbc;

import java.sql.Connection;

public interface ConnectionHolder {
    Connection getConnection();
    boolean hasConnection();
    void setConnection(Connection conn);
}
