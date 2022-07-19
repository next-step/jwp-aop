package study.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {

    @Test
    void upperCase() {
        Hello sut = (Hello) Proxy.newProxyInstance(DynamicInvocationHandler.class.getClassLoader(),
                new Class[]{Hello.class}, new DynamicInvocationHandler(new HelloTarget()));

        assertThat(sut.sayHello("sungjun")).isEqualTo("HELLO SUNGJUN");
        assertThat(sut.sayHi("sungjun")).isEqualTo("HI SUNGJUN");
        assertThat(sut.sayThankYou("sungjun")).isEqualTo("THANK YOU SUNGJUN");
        assertThat(sut.pingpong("sungjun")).isEqualTo("Pong sungjun");
    }
}
