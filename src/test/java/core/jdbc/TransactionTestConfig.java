package core.jdbc;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import core.annotation.Bean;
import core.annotation.ComponentScan;
import core.annotation.Configuration;

@Configuration
@ComponentScan({"core.jdbc", "next"})
public class TransactionTestConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem://localhost/~/jwp-aop;MODE=MySQL;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
