package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.hello.Hello;
import study.hello.HelloTarget;

import static core.aop.AdvisorTest.SAY_METHOD_UPPERCASE_ADVISOR;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("프록시 팩토리 빈")
class ProxyFactoryBeanTest {

    @Test
    @DisplayName("클래스, 객체, 어드바이저로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() ->
                ProxyFactoryBean.of(Hello.class, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR));
    }

    @Test
    @DisplayName("클래스, 객체, 어드바이저는 필수")
    void instance_null_thrownIllegalArgumentException() {
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        ProxyFactoryBean.of(null, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR)),
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        ProxyFactoryBean.of(Hello.class, null, SAY_METHOD_UPPERCASE_ADVISOR)),
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        ProxyFactoryBean.of(Hello.class, new HelloTarget(), null))
        );
    }

    @Test
    @DisplayName("구현 클래스의 프록시 객체 생성")
    void newProxy_concrete() {
        //given
        ProxyFactoryBean<HelloTarget> uppercaseSayHelloProxyFactory = ProxyFactoryBean.of(HelloTarget.class, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR);
        //when
        HelloTarget uppercaseSayHello = uppercaseSayHelloProxyFactory.object();
        //then
        assertAll(
                () -> assertThat(uppercaseSayHello).isNotNull(),
                () -> assertThat(uppercaseSayHello.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(uppercaseSayHello.sayHi("yong")).isEqualTo("HI YONG"),
                () -> assertThat(uppercaseSayHello.sayThankYou("yong")).isEqualTo("THANK YOU YONG"),
                () -> assertThat(uppercaseSayHello.pingpong("yong")).isEqualTo("Pong yong")
        );
    }

}
