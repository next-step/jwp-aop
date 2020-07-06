package core.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> holder = new ThreadLocal<>();
    private static DataSource dataSource;

    public static Connection getConnectionForTransaction() {
        if (holder.get() == null) {
            initConnection();
        }

        return holder.get();
    }

    public static Connection getConnection() {
        try {
            if (holder.get() != null) {
                return holder.get();
            } else {
                return getDataSource().getConnection();
            }
        } catch (SQLException throwables) {
            throw new DataAccessException("Fail to get connection " + throwables.getMessage());
        }
    }

    private static void initConnection() {
        try {
            Connection connection = getDataSource().getConnection();
            holder.set(connection);
        } catch (Exception e) {
            throw new DataAccessException("Fail to get connection for transaction " + e.getMessage());
        }
    }


    public static void releaseConnection() {
        holder.remove();
    }

    public static void setDataSource(DataSource dataSource) {
        ConnectionHolder.dataSource = dataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
