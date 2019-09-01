package study.jdk;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-09-01
 */
public class JdkProxyTest {
    @Test
    void toUppercase() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class},
                new UppercaseHandler());
        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("pong javajigi");
    }
}
