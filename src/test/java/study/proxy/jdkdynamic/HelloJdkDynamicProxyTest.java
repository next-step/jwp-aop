package study.proxy.jdkdynamic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HelloJdkDynamicProxyTest {

    @DisplayName("핸들러 객체를 사용한 프록시를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxy() {
        HelloTarget target = new HelloTarget();
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                HelloJdkDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new ToUpperInvocationHandler(target));
        String name = "J";

        assertAll("모든 메서드 대문자 반환",
                () -> assertThat(proxyInstance.sayHello(name)).isEqualTo(target.sayHello(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayHi(name)).isEqualTo(target.sayHi(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayThankYou(name)).isEqualTo(target.sayThankYou(name).toUpperCase())
        );
    }
}
