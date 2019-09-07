package study.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HelloCglibProxyTest {
    @DisplayName("람다를 사용한 CGLIB 프록시를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyUsingLambda() {
        toUpperProxy((MethodInterceptor) (obj, method, args, proxy) -> {
            Object result = proxy.invokeSuper(obj, args);
            if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                return ((String) result).toUpperCase();
            }
            return result;
        });
    }

    @DisplayName("인터셉터 객체를 사용한 CGLIB 프록시를 적용하여 모든 메서드 대문자 반환")
    @Test
    void toUpperProxyUsingInstanceOfInterceptor() {
        toUpperProxy(new ToUppserMethodInterceptor());
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
                () -> assertThat(proxyInstance.sayThankYou(name)).isEqualTo(expected.sayThankYou(name).toUpperCase())
        );
    }
}
