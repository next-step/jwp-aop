package core.di.beans.factory.support.proxy;

import core.di.beans.factory.support.proxy.example.HelloFactoryBean;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.proxy.example.Hello;
import study.proxy.example.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryBeanTest {

    ApplicationContext context;

    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext();
    }

    @Test
    public void helloFactoryBean() throws Exception {
        HelloFactoryBean factoryBean = new HelloFactoryBean();
        Hello hello = factoryBean.getObject();

        assertThat(hello.sayHello("spring")).isEqualTo("HELLO SPRING");
        assertThat(hello.sayHi("spring")).isEqualTo("HI SPRING");
        assertThat(hello.sayThankYou("spring")).isEqualTo("THANK YOU SPRING");
    }

    @Test
    public void getBean() throws Exception {
        Hello hello = context.getBean(HelloTarget.class);

        assertThat(hello.sayHello("spring")).isEqualTo("HELLO SPRING");
        assertThat(hello.sayHi("spring")).isEqualTo("HI SPRING");
        assertThat(hello.sayThankYou("spring")).isEqualTo("THANK YOU SPRING");
    }
}
