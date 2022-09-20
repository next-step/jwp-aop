package study.proxy;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.di.aop.Advice;
import core.di.aop.FactoryBean;
import core.di.aop.MethodInvocation;
import core.di.aop.PointcutAdvisor;
import core.di.aop.ProxyFactoryBean;
import core.di.aop.SayMethodPointcut;

@Configuration
public class ProxyConfig {

    @Bean
    public FactoryBean<Hello> hello() {
        final HelloTarget target = new HelloTarget();

        final Advice advice = MethodInvocation::proceed;
        final PointcutAdvisor advisor = new PointcutAdvisor(advice, SayMethodPointcut.getInstance());

        return new ProxyFactoryBean<>(target, advisor);
    }

    @Bean
    public FactoryBean<HelloService> helloService() {
        final Advice advice = MethodInvocation::proceed;
        final PointcutAdvisor advisor = new PointcutAdvisor(advice, SayMethodPointcut.getInstance());

        return new ProxyFactoryBean<>(new HelloService(), advisor);
    }
}
