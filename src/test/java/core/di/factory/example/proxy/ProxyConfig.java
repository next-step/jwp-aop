package core.di.factory.example.proxy;

import core.annotation.Bean;
import core.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Bean
    public ProxyTargetBeanFactory proxyTargetBeanFactory() {
        return new ProxyTargetBeanFactory();
    }

}
