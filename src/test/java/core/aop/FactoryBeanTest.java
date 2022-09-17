package core.aop;

import core.aop.example.TestTarget;
import core.aop.example.TestTargetFactoryBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FactoryBeanTest {

    @DisplayName("FactoryBean 인터페이스를 구현하여 프록시 객체를 생성한다.")
    @Test
    void create() {
        TestTargetFactoryBean factoryBean = new TestTargetFactoryBean();

        assertAll(
            () -> assertThat(factoryBean.getObjectType()).isEqualTo(TestTarget.class),
            () -> assertThat(factoryBean.getObject().sayHello("Jack")).isEqualTo("HELLO JACK"),
            () -> assertThat(factoryBean.getObject().pingPong("Jack")).isEqualTo("Pong Jack")
        );
    }
}
