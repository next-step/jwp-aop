package core.aop.factorybean;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.aop.*;

@Configuration
public class ProxyFactory {

    @Bean
    public FactoryBean<Hello> hello() {
        HelloTarget helloTarget = new HelloTarget();
        ProxyFactoryBean<Hello> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(helloTarget);
        proxyFactoryBean.setSuperClass(Hello.class);
        proxyFactoryBean.setInvocationHandler(new DynamicInvocationHandler(helloTarget, new PrefixSayMatcher()));
        return proxyFactoryBean;
    }
}
