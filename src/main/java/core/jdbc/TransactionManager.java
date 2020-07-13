package core.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author KingCjy
 */
public class TransactionManager {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);

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
        if(connections.get() == null) {
            connections.set(connection);
            return;
        }
        holdConnections.set(connection);
        logger.info("           ConnectionHolding Start");
    }

    public static Connection getHoldConnection() {
        if(holdConnections.get() != null) {
            return holdConnections.get();
        }
        return connections.get();
    }

    public static void releaseHoldConnection() {
        holdConnections.set(null);
        logger.info("           ConnectionHolding END");
    }

    public static void finishTransaction() {
        holdConnections.set(null);
        connections.set(null);
        actives.set(false);
    }
}
