package study.proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;


public class DynamicProxyTest {

    private Hello proxyInstance;

    @BeforeEach
    void setUp() {
        proxyInstance = (Hello) Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget())
        );
    }

    @Test
    void sayHi() {
        assertThat(proxyInstance.sayHi("jinho")).isEqualTo("HI JINHO");
    }

    @Test
    void sayHello() {
        assertThat(proxyInstance.sayHello("jinho")).isEqualTo("HELLO JINHO");
    }

    @Test
    void sayThankYou() {
        assertThat(proxyInstance.sayThankYou("jinho")).isEqualTo("THANK YOU JINHO");
    }
}
