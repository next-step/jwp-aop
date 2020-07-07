package core.jdbc;

import core.di.context.support.AnnotationConfigApplicationContext;
import next.config.MyConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("커넥션 홀더에 대한 테스트")
class ConnectionHolderTest {

    @BeforeEach
    void setEnv() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        DataSource dataSource = ac.getBean(DataSource.class);
        ConnectionHolder.setDataSource(dataSource);
    }

    @Test
    @DisplayName("동일한 스레드 내에서는 동일한 커넥션을 가져온다")
    void getConnectionInSameThread() {
        Connection connection = ConnectionHolder.getConnectionForTransaction();
        Connection anotherConnection = ConnectionHolder.getConnectionForTransaction();

        assertThat(connection).isEqualTo(anotherConnection);
    }

    @Test
    @DisplayName("다른 스레드에서는 다른 커넥션을 가져온다")
    void getConnectionInDifferentThread() {
        Connection connection = ConnectionHolder.getConnectionForTransaction();

        Thread thread = new Thread(() -> {
            Connection connectionForTransaction = ConnectionHolder.getConnectionForTransaction();
            assertThat(connection).isNotEqualTo(connectionForTransaction);
        });

        thread.start();
    }
}