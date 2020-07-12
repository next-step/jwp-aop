package study.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;
import study.proxy.cglib.UppercaseInterceptor;
import study.proxy.jdk.Hello;
import study.proxy.jdk.HelloImplTarget;
import study.proxy.jdk.UppercaseHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {

    @DisplayName("JDK Dynamic Proxy 를 사용해 모든 메소드의 값을 대문자로 변환한다.")
    @Test
    void jdkProxyTest() {
        /* given */
        InvocationHandler invocationHandler = new UppercaseHandler(new HelloImplTarget());

        /* when */
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                invocationHandler);

        /* then */
        assertThat(proxiedHello.sayHello("nick")).isEqualTo("HELLO NICK");
        assertThat(proxiedHello.sayHi("nick")).isEqualTo("HI NICK");
        assertThat(proxiedHello.sayThankYou("nick")).isEqualTo("THANK YOU NICK");
    }

    @DisplayName("CGLib Proxy 를 사용해 모든 메소드의 값을 대문자로 변환한다.")
    @Test
    void cglibProxyTest() {
        /* given */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UppercaseInterceptor());

        /* when */
        HelloTarget proxiedHelloTarget = (HelloTarget) enhancer.create();

        /* then */
        assertThat(proxiedHelloTarget.sayHello("nick")).isEqualTo("HELLO NICK");
        assertThat(proxiedHelloTarget.sayHi("nick")).isEqualTo("HI NICK");
        assertThat(proxiedHelloTarget.sayThankYou("nick")).isEqualTo("THANK YOU NICK");
    }

}
