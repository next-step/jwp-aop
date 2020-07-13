package core.di.factory.example;

import core.annotation.ComponentScan;
import core.annotation.Configuration;

@Configuration
@ComponentScan({"core.di.factory.example2", "core.di.factory.example3"})
public class TestConfiguration {
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName("org.h2.Driver");
//        ds.setUrl("jdbc:h2:~/jwp-framework;DB_CLOSE_DELAY=-1");
//        ds.setUsername("sa");
//        ds.setPassword("");
//        return ds;
//    }
}
