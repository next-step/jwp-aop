package study.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UppercaseInvocationHandlerTest {

    @DisplayName("대문자로 변환하는 JDK Dynamic Proxy")
    @Test
    void toUppercase() {
        final Hello hello = new HelloTarget();

        assertThat(hello.sayHello("Yongju")).isEqualTo("Hello Yongju");
        assertThat(hello.sayHi("Yongju")).isEqualTo("Hi Yongju");
        assertThat(hello.sayThankYou("Yongju")).isEqualTo("Thank You Yongju");

        final UppercaseInvocationHandler invocationHandler = new UppercaseInvocationHandler(hello);
        final Hello actual = (Hello) Proxy.newProxyInstance(
            Hello.class.getClassLoader(), new Class[]{Hello.class}, invocationHandler
        );

        assertThat(actual.sayHello("Yongju")).isEqualTo("HELLO YONGJU");
        assertThat(actual.sayHi("Yongju")).isEqualTo("HI YONGJU");
        assertThat(actual.sayThankYou("Yongju")).isEqualTo("THANK YOU YONGJU");
        assertThat(actual.getClass().toString()).contains("$Proxy");
    }

}
