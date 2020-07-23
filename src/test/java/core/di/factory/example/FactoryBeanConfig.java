package core.di.factory.example;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.aop.FactoryBean;
import study.aop.cglib.HelloTarget;

/**
 * Created by iltaek on 2020/07/21 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
@Configuration
public class FactoryBeanConfig {

    @Bean
    public FactoryBean<HelloTarget> getHelloTargetFactoryBean() {
        return new HelloTargetFactoryBean();
    }
}
