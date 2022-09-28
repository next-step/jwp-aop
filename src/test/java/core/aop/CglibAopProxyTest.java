package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CglibAopProxyTest {
    @Test
    @DisplayName("CglibAopProxy 생성 및 Proxy 테스트 (say 로 시작하지 않는 메소드는 대문자로 변경 X")
    void cglibAopProxyTest() {
        HelloTarget helloTarget = new HelloTarget();
        Advisor advisor = new Advisor(UpperCaseAdvice.getInstance(), SayPrefixPointCut.getInstance());
        CglibAopProxy cglibAopProxy = new CglibAopProxy(helloTarget, advisor);

        HelloTarget proxy = (HelloTarget) cglibAopProxy.getProxy();

        assertAll(
                () -> assertThat(proxy.sayHello("wu2ee")).isEqualTo("HELLO WU2EE"),
                () -> assertThat(proxy.sayHi("wu2ee")).isEqualTo("HI WU2EE"),
                () -> assertThat(proxy.sayThankYou("wu2ee")).isEqualTo("THANK YOU WU2EE"),
                () -> assertThat(proxy.pingPong("wu2ee")).isEqualTo("Pong wu2ee")
        );
    }
}