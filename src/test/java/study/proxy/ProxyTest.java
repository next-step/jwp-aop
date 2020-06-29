package study.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyTest implements MethodMatcher {
    @Test
    @DisplayName("JDK Dynamic Proxy로 특정 메소드의 반환값을 대문자로 변경하는 테스트")
    void test_JdkDynamicProxy() {
        Hello proxy = (Hello) createJdkDynamicProxyInstance();

        String expectedName = "NINJASUL";

        assertThat(proxy.sayHi(expectedName.toLowerCase())).isEqualTo("HI " + expectedName);
        assertThat(proxy.sayHello(expectedName.toLowerCase())).isEqualTo("HELLO " + expectedName);
        assertThat(proxy.sayThankYou(expectedName.toLowerCase())).isEqualTo("THANK YOU " + expectedName);
        assertThat(proxy.pingpong(expectedName)).isEqualTo("Pong " + expectedName);
    }

    private Object createJdkDynamicProxyInstance() {
        return Proxy.newProxyInstance(
            ProxyTest.class.getClassLoader(),
            new Class[] { Hello.class },
            new UppercaseHandler(new HelloJdkDynamicTarget())
        );
    }

    @Test
    @DisplayName("CglibProxy를 이용하여 특정 메소드의 반환값을 대문자로 변경하는 테스트")
    void test_CglibProxy() {
        HelloCglibTarget proxy = (HelloCglibTarget) createCglibProxyInstance();
        String expectedName = "NINJASUL";

        assertThat(proxy.sayHi(expectedName.toLowerCase())).isEqualTo("HI " + expectedName);
        assertThat(proxy.sayHello(expectedName.toLowerCase())).isEqualTo("HELLO " + expectedName);
        assertThat(proxy.sayThankYou(expectedName.toLowerCase())).isEqualTo("THANK YOU " + expectedName);
        assertThat(proxy.pingpong(expectedName)).isEqualTo("Pong " + expectedName);
    }

    private Object createCglibProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloCglibTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (matches(method, obj.getClass(), args)) {
                return ((String)proxy.invokeSuper(obj, args)).toUpperCase();
            }
            else {
                return proxy.invokeSuper(obj, args);
            }
        });

        return enhancer.create();
    }

}
