package study.cglib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Dynamic Proxy 연습")
class JDKDynamicProxyTest {

    @Test
    @DisplayName("프록시 객체의 메소드를 호출하면 대문자")
    void proxyMethod_say_uppercase() {
        //given
        Hello proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHelloInvocationHandler(new HelloTarget()));
        //when, then
        assertAll(
                () -> assertThat(proxyHello.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxyHello.sayHi("yong")).isEqualTo("HI YONG"),
                () -> assertThat(proxyHello.sayThankYou("yong")).isEqualTo("THANK YOU YONG")
        );
    }

    @Test
    @DisplayName("람다로 만든 프록시 객체의 메소드를 호출하면 대문자")
    void lambdaProxyMethod_say_uppercase() {
        //given
        HelloTarget target = new HelloTarget();
        Hello proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                (proxy, method, args) -> method.invoke(target, args).toString().toUpperCase());
        //when, then
        assertAll(
                () -> assertThat(proxyHello.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxyHello.sayHi("yong")).isEqualTo("HI YONG"),
                () -> assertThat(proxyHello.sayThankYou("yong")).isEqualTo("THANK YOU YONG")
        );
    }
}
