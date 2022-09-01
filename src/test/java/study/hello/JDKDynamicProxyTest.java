package study.hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Dynamic Proxy 연습")
class JDKDynamicProxyTest {

    public static final StartNameMethodMatcher SAY_START_NAME_METHOD_MATCHER = new StartNameMethodMatcher("say");

    @Test
    @DisplayName("프록시 객체의 메소드를 호출하면 say로 시작하는 메소드만 ")
    void proxyMethod_say_uppercase() {
        //given
        Hello proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHelloInvocationHandler(new HelloTarget(), SAY_START_NAME_METHOD_MATCHER));
        //when, then
        assertAll(
                () -> assertThat(proxyHello.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxyHello.sayHi("yong")).isEqualTo("HI YONG"),
                () -> assertThat(proxyHello.sayThankYou("yong")).isEqualTo("THANK YOU YONG"),
                () -> assertThat(proxyHello.pingpong("yong")).isEqualTo("Pong yong")
        );
    }

    @Test
    @DisplayName("람다로 만든 프록시 객체의 메소드를 호출하면 say로 시작하는 메소드만 ")
    void lambdaProxyMethod_say_uppercase() {
        //given
        HelloTarget target = new HelloTarget();
        Hello proxyHello = (Hello) Proxy.newProxyInstance(JDKDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                (proxy, method, args) -> {
                    Object result = method.invoke(target, args);
                    if (SAY_START_NAME_METHOD_MATCHER.matches(method, target.getClass(), args)) {
                        return result.toString().toUpperCase();
                    }
                    return result;
                });
        //when, then
        assertAll(
                () -> assertThat(proxyHello.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxyHello.sayHi("yong")).isEqualTo("HI YONG"),
                () -> assertThat(proxyHello.sayThankYou("yong")).isEqualTo("THANK YOU YONG"),
                () -> assertThat(proxyHello.pingpong("yong")).isEqualTo("Pong yong")
        );
    }
}
