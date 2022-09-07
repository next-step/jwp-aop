package study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.HelloMethodMatcher;

import static org.assertj.core.api.Assertions.assertThat;

class CglibProxyTest {

    @DisplayName("CGLIB 프록시 - 모든 메서드의 반환 값을 대문자로 변환한다.")
    @Test
    void toUppercase() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloMethodInterceptor(new HelloTarget(), new HelloMethodMatcher("say")));

        HelloTarget proxy = (HelloTarget) enhancer.create();

        assertThat(proxy.sayHello("proxy")).isEqualTo("HELLO PROXY");
        assertThat(proxy.sayHi("proxy")).isEqualTo("HI PROXY");
        assertThat(proxy.sayThankYou("proxy")).isEqualTo("THANK YOU PROXY");
        assertThat(proxy.pingPong("proxy")).isEqualTo("Pong proxy");
    }
}
