package study.proxy.factorybean;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.di.beans.factory.aop.Advice;
import core.di.beans.factory.aop.Aspect;
import core.di.beans.factory.aop.Pointcut;
import core.di.beans.factory.aop.ProxyFactoryBean;
import core.di.beans.factory.aop.Target;
import study.proxy.cglib.HelloTarget;

@Configuration
public class ProxyConfiguration {


    @Bean
    public ProxyFactoryBean helloTarget() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new Target(new HelloTarget(), HelloTarget.class));
        proxyFactoryBean.setAspect(toUpperCaseAspect());

        return proxyFactoryBean;
    }

    private Aspect toUpperCaseAspect() {
        return new Aspect(prefixPointcut(), toUpperCaseAdvice());
    }

    private Advice toUpperCaseAdvice() {
        return new ToUpperCaseAdvice();
    }

    private Pointcut prefixPointcut() {
        return new PrefixPointcut("say");
    }
}
