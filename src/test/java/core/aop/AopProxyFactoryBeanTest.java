package core.aop;

import hello.Hello;
import hello.HelloTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static core.aop.AdvisorTest.SAY_METHOD_UPPERCASE_ADVISOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AopProxyFactoryBeanTest {
    @Test
    @DisplayName("인터페이스의 프록시 객체 생성")
    void newProxy_interface() {
        AopProxyFactoryBean<Hello> uppercaseSayHelloAopProxyFactory = new AopProxyFactoryBean(Hello.class, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR);

        Hello uppercaseSayHello = uppercaseSayHelloAopProxyFactory.object();

        assertAll(
                () -> assertThat(uppercaseSayHello).isNotNull(),
                () -> assertThat(uppercaseSayHello.sayHello("hun")).isEqualTo("HELLO HUN"),
                () -> assertThat(uppercaseSayHello.sayHi("hun")).isEqualTo("HI HUN"),
                () -> assertThat(uppercaseSayHello.sayThankYou("hun")).isEqualTo("THANK YOU HUN"),
                () -> assertThat(uppercaseSayHello.pingpong("hun")).isEqualTo("Pong hun")
        );
    }

    @Test
    @DisplayName("구현 클래스의 프록시 객체 생성")
    void newProxy_concrete() {
        AopProxyFactoryBean<HelloTarget> uppercaseSayHelloAopProxyFactory = new AopProxyFactoryBean(HelloTarget.class, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR);

        HelloTarget uppercaseSayHello = uppercaseSayHelloAopProxyFactory.object();

        assertAll(
                () -> assertThat(uppercaseSayHello).isNotNull(),
                () -> assertThat(uppercaseSayHello.sayHello("hun")).isEqualTo("HELLO HUN"),
                () -> assertThat(uppercaseSayHello.sayHi("hun")).isEqualTo("HI HUN"),
                () -> assertThat(uppercaseSayHello.sayThankYou("hun")).isEqualTo("THANK YOU HUN"),
                () -> assertThat(uppercaseSayHello.pingpong("hun")).isEqualTo("Pong hun")
        );
    }
}