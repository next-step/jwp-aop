package core.jdbc;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionHolderTest {
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        dataSource = applicationContext.getBean(DataSource.class);
    }

    @DisplayName("현재 스레드에 포함되어 있는 커넥션을 가져온다.")
    @Test
    void hasConnection() throws SQLException {
        Connection currentConnection = dataSource.getConnection();
        ConnectionHolder.setConnection(currentConnection);
        assertThat(ConnectionHolder.getConnection()).isEqualTo(currentConnection);
    }

    @DisplayName("커넥션이 현재 스레드에 포함되어 있는지 확인한다.")
    @Test
    void setConnection() throws SQLException {
        assertThat(ConnectionHolder.hasConnection()).isFalse();
        ConnectionHolder.setConnection(dataSource.getConnection());
        assertThat(ConnectionHolder.hasConnection()).isTrue();
    }

    @DisplayName("현재 스레드의 커넥션을 close 한다.")
    @Test
    void releaseConnection() throws SQLException {
        ConnectionHolder.setConnection(dataSource.getConnection());
        ConnectionHolder.releaseConnection();
        assertThat(ConnectionHolder.getConnection()).isNull();
    }
}