package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {

    public Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
    }

    @Test
    void sayHi() {
        enhancer.setCallback(new UpperCaseInterceptor());
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.sayHi("jinho")).isEqualTo("HI JINHO");
    }

    @Test
    void sayHello() {
        enhancer.setCallback(new UpperCaseInterceptor());
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.sayHello("jinho")).isEqualTo("HELLO JINHO");
    }

    @Test
    void sayThankYou() {
        enhancer.setCallback(new UpperCaseInterceptor());
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.sayThankYou("jinho")).isEqualTo("THANK YOU JINHO");
    }

    @Test
    void pingpong() {
        enhancer.setCallback(new UpperCaseInterceptor());
        Object proxyInstance = enhancer.create();

        HelloTarget helloTarget = (HelloTarget) proxyInstance;
        assertThat(helloTarget.pingpong("jinho")).isEqualTo("Pong jinho");
    }
}
