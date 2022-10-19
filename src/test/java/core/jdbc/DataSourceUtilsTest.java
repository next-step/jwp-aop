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
import static org.junit.jupiter.api.Assertions.*;

class DataSourceUtilsTest {
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
        dataSource = applicationContext.getBean(DataSource.class);
    }

    @DisplayName("커넥션이 이미 존재할 경우 현재 스레드의 커넥션을 반환한다.")
    @Test
    void getAlreadyExistsConnection() throws SQLException {
        Connection currentConnection = dataSource.getConnection();
        DataSourceUtils.setConnection(currentConnection);
        assertThat(DataSourceUtils.getConnection(dataSource)).isEqualTo(currentConnection);
    }

    @DisplayName("커넥션이 없는 경우 새로운 커넥션을 반환한다. (스레드가 관리하는 커넥션 홀더에는 해당 커넥션을 셋팅하지 않는다.)")
    @Test
    void getNewConnection() {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        assertThat(ConnectionHolder.hasConnection()).isFalse();
        assertThat(connection).isNotNull();
    }

    @DisplayName("인자로 넘겨받은 커넥션이 현재 스레드에서 관리하는 커넥션과 같다면 Connection 을 close 하지 않는다.")
    @Test
    void noCloseConnection() throws SQLException {
        Connection currentConnection = dataSource.getConnection();
        DataSourceUtils.setConnection(currentConnection);
        DataSourceUtils.releaseConnection(currentConnection);
        assertThat(currentConnection.isClosed()).isFalse();
    }

    @DisplayName("현재 스레드에서 관리하는 커넥션이 없다면 인자로 넘겨받은 커넥션을 close 한다.")
    @Test
    void closeConnection() throws SQLException {
        Connection currentConnection = dataSource.getConnection();
        DataSourceUtils.releaseConnection(currentConnection);
        assertThat(currentConnection.isClosed()).isTrue();
    }

}