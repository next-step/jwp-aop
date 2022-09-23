package aop;

import aop.methodMatcher.MethodMatcherPrefixSay;
import aop.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CglibProxyTest {
    Enhancer enhancer;

    @BeforeEach
    void setup() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new MethodInterceptor(new MethodMatcherPrefixSay()));
    }

    @DisplayName("Hello 객체의 리턴 값은 대문자여야 한다")
    @Test
    void cglib_string_upper() {
        Object proxy = enhancer.create();
        Hello helloProxy = (Hello) proxy;
        Assertions.assertThat(helloProxy.sayHello("yo")).isEqualTo("HELLO YO");
        Assertions.assertThat(helloProxy.sayHi("yo")).isEqualTo("HI YO");
        Assertions.assertThat(helloProxy.sayThankYou("yo")).isEqualTo("THANK YOU YO");
        assertThat(helloProxy.pingpong("javajigi")).isEqualTo("Pong javajigi");

    }
}
