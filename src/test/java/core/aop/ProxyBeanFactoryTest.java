package core.aop;

import core.aop.example.ProxyConfig;
import core.aop.example.TestTarget;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProxyBeanFactoryTest {

    private DefaultBeanFactory beanFactory;

    @BeforeEach
    void setup() {
        beanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(ProxyConfig.class);
        beanFactory.preInstantiateSingletons();
    }

    @DisplayName("Bean 으로 등록된 FactoryBean 인터페이스 구현체룰 조회한다.")
    @Test
    void getProxyBean() {
        TestTarget bean = beanFactory.getBean(TestTarget.class);

        assertAll(
            () -> assertThat(bean).isNotNull(),
            () -> assertThat(bean.sayHello("Jack")).isEqualTo("HELLO JACK"),
            () -> assertThat(bean.pingPong("Jack")).isEqualTo("Pong Jack")
        );
    }
}
