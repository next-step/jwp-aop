package core;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.aop.*;

@Configuration
public class ProxyFactory {
    @Bean
    public FactoryBean<Hello> hello() {
        final HelloTarget target = new HelloTarget();

        final ProxyFactoryBean<Hello> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.setInterfaceBean(Hello.class);
        proxyFactoryBean.setInvocationHandler((proxy, method, args) -> method.invoke(target, args));

        return proxyFactoryBean;
    }

    @Bean
    public FactoryBean<HelloCGLibTarget> helloCGLibTarget() {
        final ProxyFactoryBean<HelloCGLibTarget> proxyFactoryBean = new ProxyFactoryBean<>();
        
        proxyFactoryBean.setTarget(new HelloCGLibTarget());
        proxyFactoryBean.setMethodInterceptor((obj, method, args, proxy) -> proxy.invoke(obj, args));

        return proxyFactoryBean;
    }
}
