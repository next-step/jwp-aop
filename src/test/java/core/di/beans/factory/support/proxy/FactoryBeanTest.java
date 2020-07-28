package core.di.beans.factory.support.proxy;

import core.di.beans.factory.support.proxy.example.HelloFactoryBean;
import org.junit.jupiter.api.Test;
import study.proxy.example.Hello;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryBeanTest {

    @Test
    public void helloFactoryBean() throws Exception {
        HelloFactoryBean factoryBean = new HelloFactoryBean();
        Hello hello = factoryBean.getObject();

        assertThat(hello.sayHello("spring")).isEqualTo("HELLO SPRING");
        assertThat(hello.sayHi("spring")).isEqualTo("HI SPRING");
        assertThat(hello.sayThankYou("spring")).isEqualTo("THANK YOU SPRING");
    }
}
