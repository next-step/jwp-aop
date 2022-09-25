package next.hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloJdkDynamicProxyTest {

    @DisplayName("Hello 인터페이스에 존재하는 메서드의 반환값을 JdkDynamicProxy를 사용하여 대문자로 출력한다.")
    @ParameterizedTest
    @ValueSource(strings = "one")
    void methodName(String name) {
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(HelloJdkDynamicProxyTest.class.getClassLoader(), new Class[]{Hello.class},
                new DynamicInvocationHandler(new HelloTarget()));

        assertAll(
                () -> assertEquals(proxyInstance.sayHello(name), "HELLO ONE"),
                () -> assertEquals(proxyInstance.sayHi(name), "HI ONE"),
                () -> assertEquals(proxyInstance.sayThankYou(name), "THANK YOU ONE")
        );
    }
}