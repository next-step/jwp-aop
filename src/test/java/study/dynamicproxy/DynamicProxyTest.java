package study.dynamicproxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {

    @Test
    void dynamic_proxy_to_upper_case_test() {
        Hello dynamicProxyInstance = (Hello) Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[] {Hello.class},
                new DynamicInvocationHandler(new HelloTarget())
        );

        assertThat(dynamicProxyInstance.sayHello("Summer")).isEqualTo("HELLO SUMMER");
        assertThat(dynamicProxyInstance.sayHi("Summer")).isEqualTo("HI SUMMER");
        assertThat(dynamicProxyInstance.sayThankYou("Summer")).isEqualTo("THANK YOU SUMMER");
    }
}
