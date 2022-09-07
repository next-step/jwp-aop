package study.proxy.jdkdynamic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class JdkDynamicProxyTest {

    @DisplayName("프록시를 적용할 대상 객체의 모든 메서드의 반환 값을 대문자로 변환한다.")
    @Test
    void toUppercase() {
        Hello target = new HelloTarget();
        InvocationHandler handler = new HelloInvocationHandler(target);

        Hello proxy = (Hello) Proxy.newProxyInstance(
            Hello.class.getClassLoader(),
            new Class[]{Hello.class},
            handler
        );

        assertThat(proxy.sayHello("proxy")).isEqualTo("HELLO PROXY");
        assertThat(proxy.sayHi("proxy")).isEqualTo("HI PROXY");
        assertThat(proxy.sayThankYou("proxy")).isEqualTo("THANK YOU PROXY");
    }
}
