package study.proxy.jdkdynamic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HelloJdkDynamicProxyTest {
    @Test
    @DisplayName("Jdk Dynamic Proxy 학습 테스트")
    void jdkDynamicProxyTest() {
        Hello helloProxy = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                new HelloUpperCaseInvocationHandler(new HelloTarget())
        );

        assertAll(
                () -> assertThat(helloProxy.sayHello("wu2ee")).isEqualTo("HELLO WU2EE"),
                () -> assertThat(helloProxy.sayHi("wu2ee")).isEqualTo("HI WU2EE"),
                () -> assertThat(helloProxy.sayThankYou("wu2ee")).isEqualTo("THANK YOU WU2EE")
        );
    }
}
