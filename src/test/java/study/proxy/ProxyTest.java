package study.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyTest {
    @Test
    @DisplayName("JDK Dynamic Proxy로 특정 메소드의 반환값을 대문자로 변경하는 테스트")
    void jdkDynamicProxy() {
        Hello proxy = (Hello) createJdkDynamicProxyInstance();


        String expectedName = "NINJASUL";
        String actualName = expectedName.toLowerCase();

        assertThat(proxy.sayHi(actualName)).isEqualTo("HI " + expectedName);
        assertThat(proxy.sayHello(actualName)).isEqualTo("HELLO " + expectedName);
        assertThat(proxy.sayThankYou(actualName)).isEqualTo("THANK YOU " + expectedName);
        assertThat(proxy.pingpong(actualName)).isEqualTo("Pong " + actualName);
    }

    private Object createJdkDynamicProxyInstance() {
        return Proxy.newProxyInstance(
            ProxyTest.class.getClassLoader(),
            new Class[]{Hello.class},
            new UppercaseHandler(new HelloJdkDynamicTarget())
        );
    }

    @Test
    @DisplayName("CglibProxy를 이용하여 특정 메소드의 반환값을 대문자로 변경하는 테스트")
    void cglibProxy() {
        HelloCglibTarget proxy = (HelloCglibTarget) createCglibProxyInstance();

        String expectedName = "NINJASUL";
        String actualName = expectedName.toLowerCase();

        assertThat(proxy.sayHi(actualName)).isEqualTo("HI " + expectedName);
        assertThat(proxy.sayHello(actualName)).isEqualTo("HELLO " + expectedName);
        assertThat(proxy.sayThankYou(actualName)).isEqualTo("THANK YOU " + expectedName);
        assertThat(proxy.pingpong(actualName)).isEqualTo("Pong " + actualName);
    }

    private Object createCglibProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloCglibTarget.class);
        enhancer.setCallback(new ConvertToUpperCaseInterceptor());

        return enhancer.create();
    }
}
