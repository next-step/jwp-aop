package core.aop.support;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void setMappedName() {
        advisor.setMappedName("say*");
        factoryBean.addAdvisor(advisor);

        TestTarget proxy = (TestTarget) factoryBean.getObject();

        assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK");
        assertThat(proxy.pingPong("Jack")).isEqualTo("Pong Jack");
    }

    @Test
    void setMappedNames() {
        advisor.setMappedNames("*Hel*", "*Pong");
        factoryBean.addAdvisor(advisor);

        TestTarget proxy = (TestTarget) factoryBean.getObject();

        assertThat(proxy.sayHello("Jack")).isEqualTo("HELLO JACK");
        assertThat(proxy.pingPong("Jack")).isEqualTo("PONG JACK");
    }
}
