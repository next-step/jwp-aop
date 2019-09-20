package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;
import static study.HelloTarget.TEXT_OF_HELLO;
import static study.HelloTarget.TEXT_OF_HI;
import static study.HelloTarget.TEXT_OF_THANK_YOU;

public class HelloProxyTest {

    private Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
    }

    @DisplayName("Hello 객체의 리턴 값은 대문자여야 한다")
    @Test
    void whenRequestThenToUpperCase() {
        String name = "name";

        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            Object target = proxy.invokeSuper(obj, args);
            return target.toString().toUpperCase();
        });

        HelloTarget proxy = (HelloTarget) enhancer.create();

        assertThat(proxy.sayHello(name)).isEqualTo(formatToUpperCase(TEXT_OF_HELLO, name));
        assertThat(proxy.sayHi(name)).isEqualTo(formatToUpperCase(TEXT_OF_HI, name));
        assertThat(proxy.sayThankYou(name)).isEqualTo(formatToUpperCase(TEXT_OF_THANK_YOU, name));
    }

    private String formatToUpperCase(String expectedText, String arg) {
        return String.format(expectedText, arg).toUpperCase();
    }
}
