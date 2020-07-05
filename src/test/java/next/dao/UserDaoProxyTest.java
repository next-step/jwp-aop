package next.dao;

import core.di.beans.factory.support.DefaultBeanDefinition;
import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoProxyTest {

    @Test
    @DisplayName("클래스 타입")
    void getClassType() {
        DefaultBeanDefinition defaultBeanDefinition = new DefaultBeanDefinition(UserDao.class);
        UserDaoProxy userDaoProxy =
                new UserDaoProxy(defaultBeanDefinition, new JdbcTemplate(ConnectionManager.getDataSource()));

        assertThat(userDaoProxy.getClassType()).isEqualTo(UserDao.class);
    }

}
