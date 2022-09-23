package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {

    @Test
    @DisplayName("CGLib Proxy 객체를 생성한다.")
    void createCGLibProxyFactoryBean() throws Exception {
        // given
        ProxyFactoryBean<HelloCGLibTarget> proxyFactoryBean = new ProxyFactoryBean<>();
        proxyFactoryBean.setTarget(new HelloCGLibTarget());
        proxyFactoryBean.setMethodInterceptor((obj, method, args, proxy) -> proxy.invoke(obj, args));

        // when
        HelloCGLibTarget helloCGLibTarget = proxyFactoryBean.getObject();

        // then
        assertThat(helloCGLibTarget.getClass().toString()).contains("CGLIB");
    }

    @Test
    @DisplayName("JDK Dynamic Proxy 객체를 생성한다.")
    void createJDKDynamicProxyFactoryBean() throws Exception {
        // given
        ProxyFactoryBean<Hello> proxyFactoryBean = new ProxyFactoryBean<>();
        Hello hello = new HelloTarget();
        proxyFactoryBean.setTarget(hello);
        proxyFactoryBean.setInterfaceBean(Hello.class);
        proxyFactoryBean.setInvocationHandler((proxy, method, args) -> method.invoke(hello, args));

        // when
        Hello helloBean = proxyFactoryBean.getObject();

        // then
        assertThat(helloBean.getClass().toString()).contains("$Proxy");
    }
}
