package core.aop.support;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.aop.example.TestInterface;
import core.aop.example.TestTarget;
import core.aop.example.UppercaseAdvice;
import core.aop.framework.ProxyFactoryBean;

class NameMatchMethodPointcutAdvisorTest {

    private final NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor(new UppercaseAdvice());
    private ProxyFactoryBean factoryBean;

    @BeforeEach
    void setup() {
        factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(new TestTarget());
    }

    @DisplayName("이름이 'say' 로 시작하는 메서드에만 Advice 를 적용한다.")
    @Test
    void setMappedName() {
        advisor.setMappedName("say*");
        factoryBean.addAdvisor(advisor);

        TestInterface proxy = (TestInterface) factoryBean.getObject();

        assertAll(
            () -> assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK"),
            () -> assertThat(proxy.sayHi("Jack")).isEqualTo("HI JACK"),
            () -> assertThat(proxy.sayThankYou("Jack")).isEqualTo("THANK YOU JACK"),
            () -> assertThat(proxy.pingPong("Jack")).isEqualTo("Pong Jack")
        );
    }

    @DisplayName("이름에 'Hel' 을 포함하거나 'Pong' 으로 끝나는 메서드에만 Advice 를 적용한다.")
    @Test
    void setMappedNames() {
        advisor.setMappedNames("*Hel*", "*Pong");
        factoryBean.addAdvisor(advisor);

        TestInterface proxy = (TestInterface) factoryBean.getObject();

        assertAll(
            () -> assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK"),
            () -> assertThat(proxy.sayHi("Jack")).isEqualTo("Hi Jack"),
            () -> assertThat(proxy.sayThankYou("Jack")).isEqualTo("Thank You Jack"),
            () -> assertThat(proxy.pingPong("Jack")).isEqualTo("PONG JACK")
        );
    }
}
