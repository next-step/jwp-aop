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
    public ProxyFactoryBean helloTargetProxy() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(helloTarget());
        proxyFactoryBean.setAspect(toUpperCaseAspect());

        return proxyFactoryBean;
    }

    private Target helloTarget() {
        return new Target(new HelloTarget(), HelloTarget.class);
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
