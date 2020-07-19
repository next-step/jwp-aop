package study.cglib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkDynamicProxyTest {

    @DisplayName("jdkDynamicProxy로 반환값을 모두 대문자로 변환한다.")
    @Test
    void jdkDynamicProxy() {

        Hello proxyInstance = (Hello) Proxy.newProxyInstance(JdkDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class}, new DynamicInvocationHandler(new HelloTarget()));

        assertThat(proxyInstance.sayHello("sehee")).isEqualTo("HELLO SEHEE");
        assertThat(proxyInstance.sayHi("sehee")).isEqualTo("HI SEHEE");
        assertThat(proxyInstance.sayThankYou("sehee")).isEqualTo("THANK YOU SEHEE");
    }
}
