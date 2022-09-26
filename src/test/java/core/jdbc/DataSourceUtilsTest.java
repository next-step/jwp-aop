package core.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataSourceUtilsTest {

    @DisplayName("connection 객체를 얻어온다")
    @Test
    void getConnection() throws SQLException {
        final Connection actual = DataSourceUtils.getConnection(ConnectionManager.getDataSource());

        assertThat(actual).isNotNull();
        assertThat(actual.isClosed()).isFalse();

    }
}
