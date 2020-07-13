package core.jdbc;

import java.sql.Connection;

/**
 * @author KingCjy
 */
public class TransactionManager {

    private static ThreadLocal<Connection> holdConnections = new ThreadLocal<>();
    private static ThreadLocal<Connection> connections = new ThreadLocal<>();
    private static ThreadLocal<Boolean> actives = new ThreadLocal<>();

    public static void startTransaction() {
        if(isTransactionActive()) {
            throw new IllegalStateException("Cannot activate transaction synchronization - already actives");
        }

        actives.set(true);
    }

    public static boolean isTransactionActive() {
        return (actives.get() != null && actives.get() == true);
    }

    public static void registerConnection(Connection connection) {
        holdConnections.set(connection);
        connections.set(connection);
    }

    public static Connection getHoldConnection() {
        return holdConnections.get();
    }

    public static void releaseHoldConnection() {
        holdConnections.set(null);
    }

    public static void finishTransaction() {
        holdConnections.set(null);
        connections.set(null);
        actives.set(false);
    }
}
