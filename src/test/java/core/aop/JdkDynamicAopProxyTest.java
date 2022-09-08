package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.hello.Hello;
import study.hello.HelloTarget;

import static core.aop.AdvisorTest.SAY_METHOD_UPPERCASE_ADVISOR;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Jdk Dynamic Aop Proxy 객체")
class JdkDynamicAopProxyTest {

    @Test
    @DisplayName("인터페이스, 객체, 어드바이저로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() ->
                JdkDynamicAopProxy.of(Hello.class, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR));
    }

    @Test
    @DisplayName("인터페이스, 객체, 어드바이저는 필수")
    void instance_null_thrownIllegalArgumentException() {
        assertAll(
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        JdkDynamicAopProxy.of(null, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR)),
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        JdkDynamicAopProxy.of(Hello.class, null, SAY_METHOD_UPPERCASE_ADVISOR)),
                () -> assertThatIllegalArgumentException().isThrownBy(() ->
                        JdkDynamicAopProxy.of(Hello.class, new HelloTarget(), null))
        );
    }

    @Test
    @DisplayName("프록시 객체 생성")
    void proxy() {
        //given, when
        Hello proxy = (Hello) JdkDynamicAopProxy.of(Hello.class, new HelloTarget(), SAY_METHOD_UPPERCASE_ADVISOR).proxy();
        //then
        assertAll(
                () -> assertThat(proxy.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxy.pingpong("yong")).isEqualTo("Pong yong")
        );
    }
}
