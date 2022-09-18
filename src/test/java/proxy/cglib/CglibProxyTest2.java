package proxy.cglib;

import static org.assertj.core.api.Assertions.assertThat;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CglibProxyTest2 {
    private Enhancer enhancer;
    private SayMethodMatcher matcher;

    @BeforeEach
    void setUp() {
        matcher = new SayMethodMatcher();
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget2.class);
    }

    @Test
    @DisplayName("say메소드는 대문자로 변환, 그 외는 그대로 반환한다.")
    void cglibProxy() {
        enhancer.setCallback(new UppercaseMethodInterceptor(matcher));
        HelloTarget2 proxy = (HelloTarget2) enhancer.create();

        assertThat(proxy.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxy.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxy.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxy.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }
}
