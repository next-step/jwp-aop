package core.aop.example;

import core.annotation.Bean;
import core.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Bean
    public TestTargetFactoryBean testTargetFactoryBean() {
        return new TestTargetFactoryBean();
    }
}
