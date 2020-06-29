package study.dynamicproxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JDKDynamicProxyTest {
    private Hello proxiedHello;

    @BeforeEach
    void setUp() {
        proxiedHello = (Hello) Proxy.newProxyInstance(
            JDKDynamicProxyTest.class.getClassLoader(),
            new Class[] { Hello.class },
            new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    void test_upperCase() {
        String expectedName = "NINJASUL";

        assertThat(proxiedHello.sayHi(expectedName.toLowerCase())).isEqualTo("HI " + expectedName);
        assertThat(proxiedHello.sayHello(expectedName.toLowerCase())).isEqualTo("HELLO " + expectedName);
        assertThat(proxiedHello.sayThankYou(expectedName.toLowerCase())).isEqualTo("THANK YOU " + expectedName);
    }
}
