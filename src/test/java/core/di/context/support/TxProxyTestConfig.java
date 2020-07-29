package core.di.context.support;

import core.annotation.Bean;
import core.annotation.ComponentScan;
import core.annotation.Configuration;
import core.jdbc.JdbcTemplate;
import core.mvc.tobe.ArgumentMatcher;
import core.mvc.tobe.HandlerConverter;
import next.security.LoginUserArgumentResolver;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"core.di.context.support"})
public class TxProxyTestConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:~/jwp-framework;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ArgumentMatcher argumentMatcher() {
        return new ArgumentMatcher();
    }

    @Bean
    public HandlerConverter handlerConverter(ArgumentMatcher argumentMatcher) {
        HandlerConverter handlerConverter = new HandlerConverter(argumentMatcher);
        handlerConverter.addArgumentResolver(loginUserArgumentResolver());
        return handlerConverter;
    }

    LoginUserArgumentResolver loginUserArgumentResolver() {
        return new LoginUserArgumentResolver();
    }

}
