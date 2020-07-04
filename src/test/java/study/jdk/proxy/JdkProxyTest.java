package study.jdk.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkProxyTest {
    private String hello_expected = "HELLO EESEUL";
    private String hi_expected = "HI EESEUL";
    private String thankYou_expected = "THANK YOU EESEUL";
    private String pingPong_expected = "Pong eeseul";

    @Test
    void proxyTest() {
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget()));

        String hello_actual = proxyInstance.sayHello("eeseul");
        String hi_actual = proxyInstance.sayHi("eeseul");
        String thankYou_actual = proxyInstance.sayThankYou("eeseul");
        String pingPong_actual = proxyInstance.pingPong("eeseul");

        assertThat(hello_actual).isEqualTo(hello_expected);
        assertThat(hi_actual).isEqualTo(hi_expected);
        assertThat(thankYou_actual).isEqualTo(thankYou_expected);
        assertThat(pingPong_actual).isEqualTo(pingPong_expected);
    }

    @Test
    void proxyText_lambda() {
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Hello.class},
                (proxy, method, args) -> {
                    String result = (String) method.invoke(new HelloTarget(), args);
                    return result.toUpperCase();
                }
        );

        String hello_actual = proxyInstance.sayHello("eeseul");
        String hi_actual = proxyInstance.sayHi("eeseul");
        String thankYou_actual = proxyInstance.sayThankYou("eeseul");

        assertThat(hello_actual).isEqualTo(hello_expected);
        assertThat(hi_actual).isEqualTo(hi_expected);
        assertThat(thankYou_actual).isEqualTo(thankYou_expected);
    }
}