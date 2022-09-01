package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("인사 Cglib Proxy")
class HelloCglibProxyTest {

    @Test
    @DisplayName("프록시 객체의 메소드를 호출하면 대문자")
    void proxyMethod_say_uppercase() {
        //given
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UppercaseHelloMethodInterceptor());
        Hello proxyHello = (Hello) enhancer.create();
        //when, then
        assertAll(
                () -> assertThat(proxyHello.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxyHello.sayHi("yong")).isEqualTo("HI YONG"),
                () -> assertThat(proxyHello.sayThankYou("yong")).isEqualTo("THANK YOU YONG")
        );
    }
}
