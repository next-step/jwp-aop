package study.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.MethodPredicateCreator;
import study.proxy.MethodResultHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HelloCglibProxyTest {
    private static final String PROXY_TARGET_START_WITH_NAME = "say";

    @DisplayName("람다를 사용한 CGLIB 프록시를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyUsingLambda() {
        toUpperProxy((MethodInterceptor) (obj, method, args, proxy) -> {
            Object result = proxy.invokeSuper(obj, args);
            return MethodResultHelper.toUpper(method, result);
        });
    }

    @DisplayName("람다를 사용한 CGLIB 프록시를 적용하여 say로 시작하는 메서드 대문자 반환")
    @Test
    void toUpperProxyStartWithNameUsingLambda() {
        toUpperStartWithNameProxy((MethodInterceptor) (obj, method, args, proxy) -> {
            Object result = proxy.invokeSuper(obj, args);
            if (!MethodPredicateCreator.startWithName(PROXY_TARGET_START_WITH_NAME).matches(method, args)) {
                return result;
            }
            return MethodResultHelper.toUpper(method, result);
        });
    }

    @DisplayName("인터셉터 객체를 사용한 CGLIB 프록시를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyUsingInstanceOfInterceptor() {
        ToUpperMethodInterceptor interceptor = new ToUpperMethodInterceptor();
        toUpperProxy(interceptor);
    }

    @DisplayName("인터셉터 객체를 사용한 CGLIB 프록시를 적용하여 say로 시작하는 메서드 대문자 반환")
    @Test
    void toUpperProxyStartWithNameUsingInstanceOfInterceptor() {
        ToUpperMethodInterceptor interceptor = new ToUpperMethodInterceptor();
        interceptor.setMethodPredicate(MethodPredicateCreator.startWithName(PROXY_TARGET_START_WITH_NAME));
        toUpperStartWithNameProxy(interceptor);
    }

    private void toUpperProxy(Callback callback) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(callback);

        HelloTarget proxyInstance = (HelloTarget) enhancer.create();
        String name = "J";
        HelloTarget expected = new HelloTarget();

        assertAll("모든 메서드 대문자 반환",
                () -> assertThat(proxyInstance.sayHello(name)).isEqualTo(expected.sayHello(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayHi(name)).isEqualTo(expected.sayHi(name).toUpperCase()),
                () -> assertThat(proxyInstance.sayThankYou(name)).isEqualTo(expected.sayThankYou(name).toUpperCase()),
                () -> assertThat(proxyInstance.pingpong(name)).isEqualTo(expected.pingpong(name).toUpperCase())
        );
    }

    private void toUpperStartWithNameProxy(Callback callback) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(callback);

        HelloTarget proxyInstance = (HelloTarget) enhancer.create();
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
