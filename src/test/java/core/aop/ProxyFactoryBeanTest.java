package core.aop;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.aop.example.TestTarget;
import core.aop.example.UppercaseAdvice;
import core.aop.framework.ProxyFactoryBean;
import study.proxy.jdkdynamic.Hello;
import study.proxy.jdkdynamic.HelloTarget;

class ProxyFactoryBeanTest {

    @DisplayName("인터페이스 기반이라면 JDK Dynamic Proxy 로 프록시를 생성한다.")
    @Test
    void jdkDynamicProxy() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        Hello proxy = (Hello) proxyFactoryBean.getObject();

        assertAll(
            () -> assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK"),
            () -> assertThat(proxy.sayHi("Jack")).isEqualTo("HI JACK"),
            () -> assertThat(proxy.sayThankYou("Jack")).isEqualTo("THANK YOU JACK"),
            () -> assertThat(proxy.pingPong("Jack")).isEqualTo("PONG JACK")
        );
    }

    @DisplayName("구체 클래스 기반이라면 CGLIB 로 프록시를 생성한다.")
    @Test
    void cglib() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new TestTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        TestTarget proxy = (TestTarget) proxyFactoryBean.getObject();

        assertAll(
            () -> assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK"),
            () -> assertThat(proxy.sayHi("Jack")).isEqualTo("HI JACK"),
            () -> assertThat(proxy.sayThankYou("Jack")).isEqualTo("THANK YOU JACK"),
            () -> assertThat(proxy.pingPong("Jack")).isEqualTo("PONG JACK")
        );
    }
}
