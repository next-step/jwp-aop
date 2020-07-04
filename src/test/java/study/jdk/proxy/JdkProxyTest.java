package study.jdk.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkProxyTest {
    @Test
    void proxyTest() {
        String hello_expected = "HELLO EESEUL";
        String hi_expected = "HI EESEUL";
        String thankYou_expected = "THANK YOU EESEUL";

        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget()));

        String hello_actual = proxyInstance.sayHello("eeseul");
        String hi_actual = proxyInstance.sayHi("eeseul");
        String thankYou_actual = proxyInstance.sayThankYou("eeseul");

        assertThat(hello_actual).isEqualTo(hello_expected);
        assertThat(hi_actual).isEqualTo(hi_expected);
        assertThat(thankYou_actual).isEqualTo(thankYou_expected);
    }
}