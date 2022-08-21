package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class PingpongProxyTest {

    @DisplayName("Dynamic Proxy - say로 시작하는 메소드만 handler 적용")
    @Test
    void dynamicProxy() {
        String name = "dean";
        UppercaseHandler uppercaseHandler = new UppercaseHandler();
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class}, uppercaseHandler);

        assertThat(proxiedHello.sayHello(name)).isEqualTo("HELLO DEAN");
        assertThat(proxiedHello.sayHi(name)).isEqualTo("HI DEAN");
        assertThat(proxiedHello.sayThankYou(name)).isEqualTo("THANK YOU DEAN");
        assertThat(proxiedHello.pingpong(name)).isEqualTo("Pong dean");
    }

    @DisplayName("CGLib Proxy - say로 시작하는 메소드만 handler 적용")
    @Test
    void cglib() {
        String name = "dean";
        MethodInterceptor methodInterceptor = new UppercaseInterceptor();
        Object obj = Enhancer.create(HelloTargetCGLib.class, methodInterceptor);

        HelloTargetCGLib proxiedHello = (HelloTargetCGLib) obj;

        assertThat(proxiedHello.sayHello(name)).isEqualTo("HELLO DEAN");
        assertThat(proxiedHello.sayHi(name)).isEqualTo("HI DEAN");
        assertThat(proxiedHello.sayThankYou(name)).isEqualTo("THANK YOU DEAN");
        assertThat(proxiedHello.pingpong(name)).isEqualTo("Pong dean");
    }
}
