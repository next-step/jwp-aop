package study.proxy.jdk;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import study.proxy.Hello;
import study.proxy.HelloTarget;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicInvocationHandlerTest {

    private static Hello proxyInstance;

    @BeforeAll
    static void staticSetUp() {
        proxyInstance = (Hello) Proxy.newProxyInstance(
                DynamicInvocationHandlerTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget()));
    }


    @Test
    void sayHelloWithProxy() {
        String result = proxyInstance.sayHello("catsbi");

        assertThat(result).isEqualTo("Hello catsbi".toUpperCase());
    }

    @Test
    void sayHiWithProxy() {
        String result = proxyInstance.sayHi("catsbi");

        assertThat(result).isEqualTo("Hi catsbi".toUpperCase());
    }

    @Test
    void sayThankYouWithProxy() {
        String result = proxyInstance.sayThankYou("catsbi");

        assertThat(result).isEqualTo("Thank You catsbi".toUpperCase());
    }
}