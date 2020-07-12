package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.jdk.Hello;
import study.proxy.jdk.HelloTarget;
import study.proxy.jdk.UppercaseHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {

    @DisplayName("JDK Dynamic Proxy 를 사용해 모든 메소드의 값을 대문자로 변환한다.")
    @Test
    void jdkProxyTest() {
        /* given */
        InvocationHandler invocationHandler = new UppercaseHandler(new HelloTarget());

        /* when */
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                invocationHandler);

        /* then */
        assertThat(proxiedHello.sayHello("nick")).isEqualTo("HELLO NICK");
        assertThat(proxiedHello.sayHi("nick")).isEqualTo("HI NICK");
        assertThat(proxiedHello.sayThankYou("nick")).isEqualTo("THANK YOU NICK");
    }

}
