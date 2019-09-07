package study.proxy.jdkdynamic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HelloJdkDynamicProxyTest {

    @DisplayName("람다 핸들러를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyWithLambda() {
        toUpperProxy((proxy, method, args) -> {
            Object result = method.invoke(new HelloTarget(), args);
            if (method.getReturnType() == String.class) {
                return ((String) result).toUpperCase();
            }
            return result;
        });
    }

    @DisplayName("핸들러 객체를 사용한 프록시를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyWithInstanceOfHandler() {
        toUpperProxy(new ToUpperInvocationHandler(new HelloTarget()));
    }

    private void toUpperProxy(InvocationHandler invocationHandler) {
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                HelloJdkDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                invocationHandler);
        String name = "J";
        HelloTarget expected = new HelloTarget();

        assertAll("모든 메서드 대문자 반환",
                () -> assertThat(proxyInstance.sayHello(name)).isEqualTo(expected.sayHello(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayHi(name)).isEqualTo(expected.sayHi(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayThankYou(name)).isEqualTo(expected.sayThankYou(name).toUpperCase())
        );
    }
}
