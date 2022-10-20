package core.di.beans;


import core.annotation.Bean;
import core.annotation.Configuration;
import core.aop.Advisor;
import core.aop.SayPrefixPointCut;
import core.aop.UpperCaseAdvice;
import core.di.beans.factory.FactoryBean;
import study.proxy.cglib.HelloTarget;

@Configuration
public class ProxyConfig {
    @Bean
    public FactoryBean<HelloTarget> helloTargetProxy() {
        Advisor advisor = new Advisor(UpperCaseAdvice.getInstance(), SayPrefixPointCut.getInstance());
        return new ProxyFactoryBean<>(new HelloTarget(), advisor);
    }
}
