package core.di.beans.factory.support.proxy;

import core.di.beans.factory.support.proxy.example.HelloFactoryBean;
import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.di.factory.example.ExampleConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.proxy.example.Hello;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryBeanTest {

    ApplicationContext context;

    @BeforeEach
    public void setUp() {
        context = new AnnotationConfigApplicationContext(ExampleConfig.class);
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
        Object hello = context.getBean(HelloFactoryBean.class);
        Hello proxyHello = (Hello) hello;

        assertThat(proxyHello.sayHello("spring")).isEqualTo("HELLO SPRING");
        assertThat(proxyHello.sayHi("spring")).isEqualTo("HI SPRING");
        assertThat(proxyHello.sayThankYou("spring")).isEqualTo("THANK YOU SPRING");
    }
}
