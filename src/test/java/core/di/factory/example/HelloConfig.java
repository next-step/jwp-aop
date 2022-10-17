package core.di.factory.example;

import core.annotation.Bean;
import core.annotation.Configuration;
import next.factory.HelloFactoryBean;
import next.hello.HelloTarget;
import next.interceptor.UppercaseMethodInterceptor;

@Configuration
public class HelloConfig {

    @Bean
    public HelloFactoryBean helloFactoryBean() {
        return new HelloFactoryBean(HelloTarget.class, new UppercaseMethodInterceptor());
    }
}