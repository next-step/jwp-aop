package core.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> holder = new ThreadLocal<>();
    private static DataSource dataSource;

    public static Connection getConnection() {
        if (holder.get() == null) {
            initConnection();
        }

        return holder.get();
    }

    private static void initConnection() {
        try {
            holder.set(dataSource.getConnection());
        } catch (Exception e) {
            throw new DataAccessException("Fail to get connection " + e.getMessage());
        }
    }

    public static void releaseConnection() {
        holder.remove();
    }

    public static void setDataSource(DataSource dataSource) {
        ConnectionHolder.dataSource = dataSource;
    }
}
