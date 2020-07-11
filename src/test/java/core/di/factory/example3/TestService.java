package core.di.factory.example3;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.annotation.Inject;

import javax.sql.DataSource;

/**
 * @author KingCjy
 */
@Configuration
public class TestService {

    @Inject
    private DataSource dataSource;

    @Bean
    public TestFactoryBean testFactoryBean() {
        return new TestFactoryBean(dataSource);
    }
}
