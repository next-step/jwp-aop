package study.proxy.jdkdynamic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.MethodPredicateCreator;
import study.proxy.MethodResultHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HelloJdkDynamicProxyTest {
    private static final String PROXY_TARGET_START_WITH_NAME = "say";

    @DisplayName("람다 핸들러를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyWithLambda() {
        toUpperProxy((proxy, method, args) -> {
            Object result = method.invoke(new HelloTarget(), args);
            return MethodResultHelper.toUpper(method, result);
        });
    }

    @DisplayName("람다 핸들러를 적용하여 say로 시작하는 메서드 대문자 반환")
    @Test
    void toUpperStartWithNameProxyWithLambda() {
        toUpperStartWithNameProxy((proxy, method, args) -> {
            Object result = method.invoke(new HelloTarget(), args);
            if (!MethodPredicateCreator.startWithName(PROXY_TARGET_START_WITH_NAME).matches(method, args)) {
                return result;
            }
            return MethodResultHelper.toUpper(method, result);
        });
    }

    @DisplayName("핸들러 객체를 사용한 프록시를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyWithInstanceOfHandler() {
        ToUpperInvocationHandler handler = new ToUpperInvocationHandler(new HelloTarget());
        toUpperProxy(handler);
    }

    @DisplayName("핸들러 객체를 사용한 프록시를 적용하여 say로 시작하는 메서드 대문자 반환")
    @Test
    void toUpperStartWithNameProxyWithInstanceOfHandler() {
        ToUpperInvocationHandler handler = new ToUpperInvocationHandler(new HelloTarget());
        handler.setMethodPredicate(MethodPredicateCreator.startWithName(PROXY_TARGET_START_WITH_NAME));
        toUpperStartWithNameProxy(handler);
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
                () -> assertThat(proxyInstance.sayThankYou(name)).isEqualTo(expected.sayThankYou(name).toUpperCase()),
                () -> assertThat(proxyInstance.pingpong(name)).isEqualTo(expected.pingpong(name).toUpperCase())
        );
    }

    private void toUpperStartWithNameProxy(InvocationHandler invocationHandler) {
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                HelloJdkDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                invocationHandler);
        String name = "J";
        HelloTarget expected = new HelloTarget();

        assertAll("모든 메서드 대문자 반환",
                () -> assertThat(proxyInstance.sayHello(name)).isEqualTo(expected.sayHello(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayHi(name)).isEqualTo(expected.sayHi(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayThankYou(name)).isEqualTo(expected.sayThankYou(name).toUpperCase()),
                () -> assertThat(proxyInstance.pingpong(name)).isEqualTo(expected.pingpong(name))
        );
    }
}
