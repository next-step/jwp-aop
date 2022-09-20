package core.aop;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.aop.example.ConcreteTarget;
import core.aop.example.TestInterface;
import core.aop.example.TestTarget;
import core.aop.example.UppercaseAdvice;
import core.aop.framework.ProxyFactoryBean;
import core.aop.intercept.MethodInterceptor;
import core.aop.intercept.MethodInvocation;

class ProxyFactoryBeanTest {

    @DisplayName("인터페이스 기반이라면 JDK Dynamic Proxy 로 프록시를 생성한다.")
    @Test
    void jdkDynamicProxy() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(new TestTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        TestInterface proxy = (TestInterface) proxyFactoryBean.getObject();

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
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(new ConcreteTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        ConcreteTarget proxy = (ConcreteTarget) proxyFactoryBean.getObject();

        assertAll(
            () -> assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK"),
            () -> assertThat(proxy.sayHi("Jack")).isEqualTo("HI JACK"),
            () -> assertThat(proxy.sayThankYou("Jack")).isEqualTo("THANK YOU JACK"),
            () -> assertThat(proxy.pingPong("Jack")).isEqualTo("PONG JACK")
        );
    }

    @DisplayName("둘 이상의 Advice 를 적용한다.")
    @Test
    void multipleAdvices() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(new ConcreteTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());
        proxyFactoryBean.addAdvice(new ExclamationMarkAdvice());

        ConcreteTarget proxy = (ConcreteTarget) proxyFactoryBean.getObject();

        assertAll(
            () -> assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK!!"),
            () -> assertThat(proxy.sayHi("Jack")).isEqualTo("HI JACK!!"),
            () -> assertThat(proxy.sayThankYou("Jack")).isEqualTo("THANK YOU JACK!!"),
            () -> assertThat(proxy.pingPong("Jack")).isEqualTo("PONG JACK!!")
        );
    }

    static class ExclamationMarkAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            return invocation.proceed() + "!!";
        }
    }
}
