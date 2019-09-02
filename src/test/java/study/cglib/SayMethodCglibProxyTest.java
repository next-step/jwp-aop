package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.jdk.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ssosso.follow on 2019-09-01
 */
public class SayMethodCglibProxyTest {

    private Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
    }

    @Test
    @DisplayName("say로 시작하는 메서드 반환값 대문자")
    void toUppercase() {
        enhancer.setCallback(new UppercaseInterceptor());
        HelloTarget proxy = (HelloTarget) enhancer.create();

        assertThat(proxy.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxy.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxy.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxy.pingpong("javajigi")).isEqualTo("pong javajigi");
    }
}
