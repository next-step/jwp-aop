package aop;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.FactoryAdvisor;
import core.aop.Pointcut;
import core.aop.factorybean.ProxyFactoryBean;
import core.aop.test.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProxyFactoryBeanTest {

    ProxyFactoryBean<CGLIBHelloTarget> cglibHelloTargetProxyFactoryBean;
    ProxyFactoryBean<Hello> jdkHelloFactoryBean;

    @BeforeEach
    void setup() {
        Advice upperAdvice = new UpperAdvice();
        Pointcut pointcut = new SayPointcut();
        Advisor advisor = new FactoryAdvisor(upperAdvice, pointcut);

        jdkHelloFactoryBean = new ProxyFactoryBean<>(new HelloTarget(), advisor);

        cglibHelloTargetProxyFactoryBean = new ProxyFactoryBean<>(new CGLIBHelloTarget(), advisor);
    }

    @Test
    void cglib_proxy_test() {
        Assertions.assertThat(cglibHelloTargetProxyFactoryBean.getObject().getClass().toString()).contains("CGLIB");
    }

    @Test
    void jdk_proxy_test() {
        Assertions.assertThat(jdkHelloFactoryBean.getObject().getClass().toString()).contains("$Proxy");
    }
}
