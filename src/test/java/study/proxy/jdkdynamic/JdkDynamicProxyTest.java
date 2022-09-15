package study.proxy.jdkdynamic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.HelloMethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JdkDynamicProxyTest {

    @DisplayName("JDK 동적 프록시 - 프록시 적용 대상 메서드의 반환 값을 대문자로 변환한다.")
    @Test
    void toUppercase() {
        Hello target = new HelloTarget();
        InvocationHandler handler = new HelloInvocationHandler(target, new HelloMethodMatcher("say"));

        Hello proxy = (Hello) Proxy.newProxyInstance(
            Hello.class.getClassLoader(),
            new Class[]{Hello.class},
            handler
        );

        assertAll(
            () -> assertThat(proxy.sayHello("proxy")).isEqualTo("HELLO PROXY"),
            () -> assertThat(proxy.sayHi("proxy")).isEqualTo("HI PROXY"),
            () -> assertThat(proxy.sayThankYou("proxy")).isEqualTo("THANK YOU PROXY"),
            () -> assertThat(proxy.pingPong("proxy")).isEqualTo("Pong proxy")
        );
    }
}
