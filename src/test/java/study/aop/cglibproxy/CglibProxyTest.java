package study.aop.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CGLIB Proxy 테스트")
public class CglibProxyTest {
    private final String name = "jeongsu";
    private HelloTarget proxyInstance;

    @BeforeEach
    void setUp() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new CustomMethodInterceptor());
        proxyInstance = (HelloTarget) enhancer.create();
    }

    @DisplayName("프록시 객체의 sayHello 메서드를 호출하면 대문자를 반환한다.")
    @Test
    void sayHello() {
        // when
        final String result = proxyInstance.sayHello(name);

        // then
        assertThat(result).isEqualTo("HELLO " + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 sayHi 메서드를 호출하면 대문자를 반환한다.")
    @Test
    void sayHi() {
        // when
        final String result = proxyInstance.sayHi(name);

        // then
        assertThat(result).isEqualTo("HI " + name.toUpperCase());
    }

    @DisplayName("프록시 객체의 sayThankYou 메서드를 호출하면 대문자를 반환한다.")
    @Test
    void sayThankYou() {
        // when
        final String result = proxyInstance.sayThankYou(name);

        // then
        assertThat(result).isEqualTo("THANK YOU " + name.toUpperCase());
    }
}
