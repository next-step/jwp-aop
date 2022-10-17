package hello;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HelloCglibProxyTest {

    @Test
    @DisplayName("프록시 객체의 메서드를 호출하면 대문자결과 반환")
    void proxyMethodUpperCase() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> proxy.invokeSuper(obj, args).toString().toUpperCase());

        Hello proxyHello = (Hello) enhancer.create();

        assertAll(
                () -> assertThat(proxyHello.sayHello("hun")).isEqualTo("HELLO HUN"),
                () -> assertThat(proxyHello.sayHi("hun")).isEqualTo("HI HUN"),
                () -> assertThat(proxyHello.sayThankYou("hun")).isEqualTo("THANK YOU HUN"),
                () -> assertThat(proxyHello.pingpong("hun")).isEqualTo("PONG HUN")
        );
    }

    @Test
    @DisplayName("프록시 객체의 메서드를 호출하면 say로 시작하는 메서드만대문자결과 반환")
    void proxyMethodUpperCaseWithSay() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {

            String result = proxy.invokeSuper(obj, args).toString();

            if (StringUtils.startsWith(method.getName(), "say")) {
                return result.toUpperCase();
            }

            return result;
        });

        Hello proxyHello = (Hello) enhancer.create();

        assertAll(
                () -> assertThat(proxyHello.sayHello("yong")).isEqualTo("HELLO YONG"),
                () -> assertThat(proxyHello.sayHi("yong")).isEqualTo("HI YONG"),
                () -> assertThat(proxyHello.sayThankYou("yong")).isEqualTo("THANK YOU YONG"),
                () -> assertThat(proxyHello.pingpong("yong")).isEqualTo("Pong yong")
        );
    }
}
