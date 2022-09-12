package core.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTargetTest {

    private Hello dynamicProxyHello;

    @BeforeEach
    void setup() {
        dynamicProxyHello = (Hello) Proxy.newProxyInstance(HelloTarget.class.getClassLoader(),
                new Class[] {Hello.class},
                new DynamicInvocationHandler(new HelloTarget()));
    }

    @Test
    @DisplayName("Java Dynamic Proxy를 사용하여 모든 메소드의 반환 값을 대문자로 변환한다.")
    void dynamicProxy() {
        // given
        final String name = "Test";

        // when
        // then
        assertThat(dynamicProxyHello.sayHello(name)).isEqualTo("HELLO TEST");
        assertThat(dynamicProxyHello.sayHi(name)).isEqualTo("HI TEST");
        assertThat(dynamicProxyHello.sayThankYou(name)).isEqualTo("THANK YOU TEST");
    }
}
