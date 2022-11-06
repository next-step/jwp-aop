package core.aop;

import core.annotation.Bean;
import core.annotation.ComponentScan;
import core.annotation.Configuration;
import core.di.beans.factory.ProxyFactoryBean;
import core.di.beans.factory.proxy.Advice;
import core.di.beans.factory.proxy.Advisor;
import core.di.beans.factory.proxy.cglib.CglibProxyFactory;
import core.di.beans.factory.proxy.jdkdynamic.JdkDynamicProxyFactory;
import study.cglib.mission1.Hello;
import study.cglib.mission1.HelloTarget;

@Configuration
@ComponentScan(basePackages = {"core.aop"})
public class TestConfiguration {

    @Bean
    public HelloTargetFactoryBean helloTarget() {
        return new HelloTargetFactoryBean();
    }

    @Bean
    public ProxyFactoryBean<Hello> helloTarget2() {
        Advice advice = joinPoint -> {
            var proceed = joinPoint.proceed();

            var result = (String)proceed;
            return result.toUpperCase();
        };

        ProxyFactoryBean<Hello> helloProxyFactoryBean = new ProxyFactoryBean<>(
            Hello.class,
            new HelloTarget(),
            new Advisor(advice, (targetClass, method) -> true),
            new JdkDynamicProxyFactory()
        );

        return helloProxyFactoryBean;
    }
}
