package hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class JDKDynamicProxyTest {

    @Test
    @DisplayName("프록시 객체의 메서드를 호출하면 대문자결과 반환")
    void proxyMethodUpperCase() {
        Hello proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(), new Class[]{Hello.class}, new HelloUppercaseInvocationHandler(new HelloTarget()));

        assertAll(
                () -> assertThat(proxyHello.sayHello("hun")).isEqualTo("HELLO HUN"),
                () -> assertThat(proxyHello.sayHi("hun")).isEqualTo("HI HUN"),
                () -> assertThat(proxyHello.sayThankYou("hun")).isEqualTo("THANK YOU HUN"),
                () -> assertThat(proxyHello.pingpong("hun")).isEqualTo("PONG HUN")
        );
    }

    @Test
    @DisplayName("프록시 객체의 메서드를 호출하면 say로 시작하는 메서드만대문자결과 반환")
    void proxyMethodUpperCaseWithSay() {
        Hello proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(), new Class[]{Hello.class}, new HelloUppercaseInvocationHandlerWithSay(new HelloTarget()));

        assertAll(
                () -> assertThat(proxyHello.sayHello("hun")).isEqualTo("HELLO HUN"),
                () -> assertThat(proxyHello.sayHi("hun")).isEqualTo("HI HUN"),
                () -> assertThat(proxyHello.sayThankYou("hun")).isEqualTo("THANK YOU HUN"),
                () -> assertThat(proxyHello.pingpong("hun")).isEqualTo("Pong hun")
        );
    }
}
