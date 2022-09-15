package study.proxy;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.di.aop.FactoryBean;
import core.di.aop.ProxyFactoryBean;
import core.di.factory.example.UserRepository;

@Configuration
public class ProxyConfig {

    @Bean
    public FactoryBean<Hello> hello() {
        final HelloTarget target = new HelloTarget();

        final ProxyFactoryBean<Hello> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.setSuperInterface(UserRepository.class);
        proxyFactoryBean.setInvocationHandler((proxy, method, args) -> method.invoke(target, args));

        return proxyFactoryBean;
    }

    @Bean
    public FactoryBean<HelloService> helloService() {
        final ProxyFactoryBean<HelloService> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(new HelloService());
        proxyFactoryBean.setMethodInterceptor((obj, method, args, proxy) -> proxy.invokeSuper(obj, args));

        return proxyFactoryBean;
    }
}
