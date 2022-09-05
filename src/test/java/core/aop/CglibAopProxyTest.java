package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.hello.HelloTarget;

import static core.aop.AdvisorTest.SAY_METHOD_UPPERCASE_ADVISOR;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Cglib Aop Proxy 객체")
class CglibAopProxyTest {

    @Test
    @DisplayName("구현 클래스, 어드바이저로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() ->
                CglibAopProxy.of(HelloTarget.class, SAY_METHOD_UPPERCASE_ADVISOR));
    }

    @Test
    @DisplayName("구현 클래스, 어드바이저는 필수")
    void instance_null_thrownIllegalArgumentException() {
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        CglibAopProxy.of(null, SAY_METHOD_UPPERCASE_ADVISOR)),
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        CglibAopProxy.of(HelloTarget.class, null))
        );
    }

    @Test
    @DisplayName("프록시 객체 생성")
    void proxy() {
        //given, when
        HelloTarget proxy = (HelloTarget) CglibAopProxy.of(HelloTarget.class, SAY_METHOD_UPPERCASE_ADVISOR).proxy();
        //then
        assertAll(
                () -> assertThat(proxy.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxy.pingpong("yong")).isEqualTo("Pong yong")
        );
    }
}
