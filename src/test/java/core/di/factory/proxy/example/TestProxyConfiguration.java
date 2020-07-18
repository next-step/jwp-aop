package core.di.factory.proxy.example;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.annotation.Inject;

import javax.sql.DataSource;

/**
 * @author KingCjy
 */
@Configuration
public class TestProxyConfiguration {

    @Inject
    private DataSource dataSource;

    @Bean
    public TestFactoryBean testFactoryBean() {
        return new TestFactoryBean(dataSource);
    }

    @Bean
    public CarDaoProxyFactoryBean myCarDao() {
        return new CarDaoProxyFactoryBean(dataSource);
    }
}
