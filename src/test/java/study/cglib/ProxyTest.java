package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {

    public Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UpperCaseInterceptor((method, targetClass, args) -> method.getName().startsWith("say") && method.getReturnType() == String.class));
    }

    @Test
    void sayHi() {
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.sayHi("jinho")).isEqualTo("HI JINHO");
    }

    @Test
    void sayHello() {
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.sayHello("jinho")).isEqualTo("HELLO JINHO");
    }

    @Test
    void sayThankYou() {
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.sayThankYou("jinho")).isEqualTo("THANK YOU JINHO");
    }

    @Test
    void pingpong() {
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.pingpong("jinho")).isEqualTo("Pong jinho");
    }
}
