package proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {

    @DisplayName("JDK Dynamic Proxy 적용")
    @Test
    void dynamicProxy() {
        String name = "dean";

        Hello upperHello = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[]{Hello.class}, (proxy, method, args) -> {
            HelloTarget helloTarget = new HelloTarget();
            return ((String) method.invoke(helloTarget, args)).toUpperCase();
        });

        assertThat(upperHello.sayHello(name)).isEqualTo("HELLO DEAN");
        assertThat(upperHello.sayHi(name)).isEqualTo("HI DEAN");
        assertThat(upperHello.sayThankYou(name)).isEqualTo("THANK YOU DEAN");
    }
}
