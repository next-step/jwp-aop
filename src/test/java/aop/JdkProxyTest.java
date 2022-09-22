package aop;

import aop.methodMatcher.MethodMatcherPrefixSay;
import aop.proxy.DynamicInvocationHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkProxyTest {

    Hello proxiedHello;

    @BeforeEach
    void setup() {
        proxiedHello = (Hello) Proxy.newProxyInstance(JdkProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget(), new MethodMatcherPrefixSay()));
    }

    @Test
    @DisplayName("say로 시작하는 메서드는 리턴 값이 대문자여야 한다")
    void toUppercase() {
        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }
}
