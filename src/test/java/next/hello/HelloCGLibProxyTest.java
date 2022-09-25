package next.hello;

import next.interceptor.UppercaseMethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class HelloCGLibProxyTest {

    @DisplayName("CGLib 을 사용하여 응답을 대문자로 받는다.")
    @ParameterizedTest
    @ValueSource(strings = "one")
    void returnUpperCase(String name) {
        EnhancerWrapper enhancerWrapper = new EnhancerWrapper(HelloTarget.class, new UppercaseMethodInterceptor());
        HelloTarget helloTarget = (HelloTarget) enhancerWrapper.create();

        assertAll(
                () -> assertEquals(helloTarget.sayHello(name), "HELLO ONE"),
                () -> assertEquals(helloTarget.sayHi(name), "HI ONE"),
                () -> assertEquals(helloTarget.sayThankYou(name), "THANK YOU ONE")
        );
    }
}