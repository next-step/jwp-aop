package core.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConnectionHolderTest {

    private Connection connection;

    @BeforeEach
    void setUp() {
        connection = ConnectionManager.getConnection();
        ConnectionHolder.setConnection(ConnectionManager.getConnection());
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
        ConnectionHolder.releaseConnection();
    }

    @DisplayName("Connection의 존재 여부를 확인한다")
    @Test
    void hasConnection() {
        final boolean actual = ConnectionHolder.hasConnection();

        assertThat(actual).isTrue();
    }

    @DisplayName("connection 객체를 얻어온다.")
    @Test
    void getConnection() {
        final Connection actual = ConnectionHolder.getConnection();

        assertThat(actual).isNotNull();
    }

    @DisplayName("connection 닫는다.")
    @Test
    void releaseConnection() {
        ConnectionHolder.releaseConnection();
        final Connection actual = ConnectionHolder.getConnection();

        assertThat(actual).isNull();
    }
}
