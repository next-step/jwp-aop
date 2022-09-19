package study.proxy;

import core.aop.DynamicInvocationHandler;
import core.aop.Hello;
import core.aop.HelloTarget;
import core.aop.SayMethodMatcher;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class JdkDynamicProxyTest {

    @Test
    void jdkProxyTest() {
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[]{Hello.class}, new DynamicInvocationHandler(new HelloTarget(), new SayMethodMatcher()));

        assertAll(
                () -> assertThat(proxyInstance.sayHello("Proxy")).isEqualTo("HELLO PROXY"),
                () -> assertThat(proxyInstance.sayHi("Proxy")).isEqualTo("HI PROXY"),
                () -> assertThat(proxyInstance.sayThankYou("Proxy")).isEqualTo("THANK YOU PROXY"),
                () -> assertThat(proxyInstance.pingPong("Proxy")).isEqualTo("Pong Proxy")
        );
    }
}
