package study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.SayPrefixMethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class CglibTest {
    private static HelloTarget proxyInstance;

    @BeforeAll
    static void staticSetup() {
        Enhancer enhancer = new Enhancer();
        HelloMethodInterceptor methodInterceptor = new HelloMethodInterceptor();
        methodInterceptor.updateMethodMatcher(new SayPrefixMethodMatcher());

        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(methodInterceptor);

        proxyInstance = (HelloTarget) enhancer.create();
    }

    @Test
    void sayHelloWithProxy() {
        String result = proxyInstance.sayHello("catsbi");

        assertThat(result).isEqualTo("Hello catsbi".toUpperCase());
    }

    @Test
    void sayHiWithProxy() {
        String result = proxyInstance.sayHi("catsbi");

        assertThat(result).isEqualTo("Hi catsbi".toUpperCase());
    }

    @Test
    void sayThankYouWithProxy() {
        String result = proxyInstance.sayThankYou("catsbi");

        assertThat(result).isEqualTo("Thank You catsbi".toUpperCase());
    }

    @DisplayName("메서드명이 say로 시작하는 메서드는 반환값이 모두 대문자로 반환된다.")
    @Test
    void executeWithValidMethodName() {
        String hello = proxyInstance.sayHello("catsbi");
        String hi = proxyInstance.sayHi("catsbi");
        String thankYou = proxyInstance.sayThankYou("catsbi");

        assertThat(hello).isEqualTo("HELLO CATSBI");
        assertThat(hi).isEqualTo("HI CATSBI");
        assertThat(thankYou).isEqualTo("THANK YOU CATSBI");
    }

    @DisplayName("메서드명이 say로 시작하지 않는 메서드는 모두 변환없이 반환된다.")
    @Test
    void executeWithInvalidMethodName() {
        String result = proxyInstance.pingpong("catsbi");

        assertThat(result).isEqualTo("pong catsbi");
    }
}
