package core.aop.tx;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;

@Setter
@Getter
public class ThreadConnection {

    private Connection connection;
    private boolean maintained;

    public ThreadConnection(Connection connection) {
        this(connection, false);
    }

    public ThreadConnection(Connection connection, boolean maintained) {
        this.connection = connection;
        this.maintained = maintained;

        try {
            connection.setAutoCommit(!maintained);
        } catch (SQLException e) {
            throw new IllegalStateException("Setting auto commit failed.", e);
        }
    }

    public void close() throws SQLException {
        if (this.connection == null) {
            return;
        }

        this.connection.close();
    }
}
