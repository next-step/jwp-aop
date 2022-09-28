package study.aop.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CGLIB Proxy 테스트")
public class CglibProxyTest {
    private final String name = "yang";
    private HelloTarget proxyInstance;

    @BeforeEach
    void setUp() {
        proxyInstance = (HelloTarget) Enhancer.create(HelloTarget.class, new CustomMethodInterceptor());
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
