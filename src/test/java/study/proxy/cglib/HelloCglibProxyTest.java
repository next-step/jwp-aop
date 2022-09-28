package study.proxy.cglib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import study.proxy.SayPrefixMethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HelloCglibProxyTest {
    @Test
    @DisplayName("Cglib Proxy 학습 테스트 (say 로 시작하지 않는 메소드는 대문자로 변경 X)")
    void cglibProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloUpperCaseMethodInterceptor(new HelloTarget(), new SayPrefixMethodMatcher()));
        HelloTarget helloProxy = (HelloTarget) enhancer.create();

        assertAll(
                () -> assertThat(helloProxy.sayHello("wu2ee")).isEqualTo("HELLO WU2EE"),
                () -> assertThat(helloProxy.sayHi("wu2ee")).isEqualTo("HI WU2EE"),
                () -> assertThat(helloProxy.sayThankYou("wu2ee")).isEqualTo("THANK YOU WU2EE"),
                () -> assertThat(helloProxy.pingPong("wu2ee")).isEqualTo("Pong wu2ee")
        );
    }
}
