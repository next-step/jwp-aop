package study.proxy;

import core.aop.*;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CgLibProxyTest {

    @Test
    void cglibProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloCglibTarget.class);
        enhancer.setCallback(new CglibMethodInterceptor(new HelloCglibTarget(), new SayMethodMatcher()));

        HelloCglibTarget helloCglibTarget = (HelloCglibTarget) enhancer.create();
        assertAll(
                () -> assertThat(helloCglibTarget.sayHello("Proxy")).isEqualTo("HELLO PROXY"),
                () -> assertThat(helloCglibTarget.sayHi("Proxy")).isEqualTo("HI PROXY"),
                () -> assertThat(helloCglibTarget.sayThankYou("Proxy")).isEqualTo("THANK YOU PROXY"),
                () -> assertThat(helloCglibTarget.pingPong("Proxy")).isEqualTo("Pong Proxy")
        );
    }
}
