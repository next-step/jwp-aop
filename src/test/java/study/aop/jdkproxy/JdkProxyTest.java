package study.aop.jdkproxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JDK Proxy 테스트")
public class JdkProxyTest {
    private final String name = "yang";
    private Hello proxyInstance;

    @BeforeEach
    void setUp() {
        proxyInstance = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget()));
    }

    @DisplayName("프록시 객체의 sayHello 메서드를 호출하면 대문자로 변환된 결과를 반환한다.")
    @Test
    void sayHello() {
        // when
        final String result = proxyInstance.sayHello(name);

        // then
        assertThat(result).isEqualTo("HELLO " + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 sayHi 메서드를 호출하면 대문자로 변환된 결과를 반환한다.")
    @Test
    void sayHi() {
        // when
        final String result = proxyInstance.sayHi(name);

        // then
        assertThat(result).isEqualTo("HI " + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 sayThankYou 메서드를 호출하면 대문자로 변환된 결과를 반환한다.")
    @Test
    void sayThankYou() {
        // when
        final String result = proxyInstance.sayThankYou(name);

        // then
        assertThat(result).isEqualTo("THANK YOU " + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 pingPong 메서드를 호출하면 대문자로 변환되지 않은 결과를 반환한다.")
    @Test
    void pingPong() {
        // when
        final String result = proxyInstance.pingPong(name);

        // then
        assertThat(result).isEqualTo("Pong " + name);
    }
}
