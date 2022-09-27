package aop;

import core.aop.*;
import core.aop.factorybean.ProxyFactoryBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProxyFactoryBeanTest {

    ProxyFactoryBean<CGLIBHelloTarget> cglibHelloTargetProxyFactoryBean;
    ProxyFactoryBean<Hello> jdkHelloFactoryBean;

    @BeforeEach
    void setup() {
        CGLIBHelloTarget cglibHelloTarget = new CGLIBHelloTarget();
        HelloTarget helloTarget = new HelloTarget();

        cglibHelloTargetProxyFactoryBean = new ProxyFactoryBean<>();
        cglibHelloTargetProxyFactoryBean.setTarget(cglibHelloTarget);
        cglibHelloTargetProxyFactoryBean.setMethodInterceptor(new UpperMethodInterceptor(new PrefixSayMatcher()));

        jdkHelloFactoryBean = new ProxyFactoryBean<>();
        jdkHelloFactoryBean.setSuperClass(Hello.class);
        jdkHelloFactoryBean.setInvocationHandler(new DynamicInvocationHandler(helloTarget, new PrefixSayMatcher()));
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
