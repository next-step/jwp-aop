package core.aop.jdk;

import core.aop.SayMethodMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicInvocationHandlerTest {

    @DisplayName("모든 메소드의 반환 값을 대문자로 변환")
    @Test
    void UpperCase() {
        final Hello proxyHello = (Hello) Proxy.newProxyInstance(
                DynamicInvocationHandlerTest.class.getClassLoader(),
                new Class[] { Hello.class },
                new DynamicInvocationHandler(new HelloTarget(), new SayMethodMatcher()));

        assertThat(proxyHello.sayHello("tester")).isEqualTo("HELLO TESTER");
        assertThat(proxyHello.sayHi("tester")).isEqualTo("HI TESTER");
        assertThat(proxyHello.sayThankYou("tester")).isEqualTo("THANK YOU TESTER");
        assertThat(proxyHello.pingpong()).isEqualTo("pingpong");
    }
}