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
            Connection connection = getDataSource().getConnection();
            System.out.println("Connection : " + connection);
            System.out.println("Connection : " + connection);
            System.out.println("Connection : " + connection);
            System.out.println("Connection : " + connection);
            System.out.println("Connection : " + connection);
            holder.set(connection);
        } catch (Exception e) {
            throw new DataAccessException("Fail to get connection " + e.getMessage());
        }
    }

    public static void releaseConnection() {
        holder.remove();
    }

    public static void setDataSource(DataSource dataSource) {
        System.out.println("Set Data Source");
        System.out.println("Set Data Source");
        System.out.println("Set Data Source");
        System.out.println("Set Data Source");
        System.out.println("Set Data Source");
        System.out.println("Set Data Source");
        ConnectionHolder.dataSource = dataSource;
    }

    public static DataSource getDataSource() {
        System.out.println("Get Data Source" + dataSource);
        System.out.println("Get Data Source" + dataSource);
        System.out.println("Get Data Source" + dataSource);
        System.out.println("Get Data Source" + dataSource);
        System.out.println("Get Data Source" + dataSource);
        System.out.println("Get Data Source" + dataSource);
        return dataSource;
    }
}
