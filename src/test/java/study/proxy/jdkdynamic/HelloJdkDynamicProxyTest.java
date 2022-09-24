package study.proxy.jdkdynamic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.NameMethodMatcher;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HelloJdkDynamicProxyTest {
    @Test
    @DisplayName("Jdk Dynamic Proxy 학습 테스트 (say 로 시작하지 않는 메소드는 대문자로 변경 X)")
    void jdkDynamicProxyTest() {
        Hello helloProxy = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                new HelloUpperCaseInvocationHandler(new HelloTargetImpl(), new NameMethodMatcher("say"))
        );

        assertAll(
                () -> assertThat(helloProxy.sayHello("wu2ee")).isEqualTo("HELLO WU2EE"),
                () -> assertThat(helloProxy.sayHi("wu2ee")).isEqualTo("HI WU2EE"),
                () -> assertThat(helloProxy.sayThankYou("wu2ee")).isEqualTo("THANK YOU WU2EE"),
                () -> assertThat(helloProxy.pingPong("wu2ee")).isEqualTo("Pong wu2ee")
        );
    }
}
