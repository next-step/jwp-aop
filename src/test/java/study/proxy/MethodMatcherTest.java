package study.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("특정 메서드만 Proxy 적용")
class MethodMatcherTest {

    @DisplayName("JDK Dynamic Proxy - say 메서드만 대문자로 변환")
    @Test
    void jdkDynamicProxyUppercaseSayMethod() {
        final Hello hello = new HelloTarget();

        final InvocationHandler invocationHandler = new UppercaseInvocationHandler(hello, new SayMethodMatcher());
        final Hello actual = (Hello) Proxy.newProxyInstance(
            Hello.class.getClassLoader(), new Class[]{Hello.class}, invocationHandler
        );

        assertThat(actual.sayHello("Yongju")).isEqualTo("HELLO YONGJU");
        assertThat(actual.sayHi("Yongju")).isEqualTo("HI YONGJU");
        assertThat(actual.sayThankYou("Yongju")).isEqualTo("THANK YOU YONGJU");
        assertThat(actual.pingPong("Yongju")).isEqualTo("PingPong Yongju");
        assertThat(actual.getClass().toString()).contains("$Proxy");
    }

    @DisplayName("CGLib Proxy - say 메서드만 대문자로 변환")
    @Test
    void cgLibProxyUppercaseSayMethod() {
        HelloService helloService = new HelloService();

        final MethodInterceptor uppercaseInterceptor = new UppercaseInterceptor(helloService, new SayMethodMatcher());
        final HelloService actual = (HelloService) Enhancer.create(HelloService.class, uppercaseInterceptor);

        assertThat(actual.sayHello("Yongju")).isEqualTo("HELLO YONGJU");
        assertThat(actual.sayHi("Yongju")).isEqualTo("HI YONGJU");
        assertThat(actual.sayThankYou("Yongju")).isEqualTo("THANK YOU YONGJU");
        assertThat(actual.pingPong("Yongju")).isEqualTo("PingPong Yongju");
        assertThat(actual.getClass().toString()).contains("CGLIB$$");
    }

    @DisplayName("JDK Dynamic Proxy - pingPong 메서드만 대문자로 변환")
    @Test
    void jdkDynamicProxyUppercasePingPongMethod() {
        final Hello hello = new HelloTarget();

        final MethodMatcher methodMatcher = (method, clazz, args) -> "pingPong".equals(method.getName());
        final InvocationHandler invocationHandler = new UppercaseInvocationHandler(hello, methodMatcher);
        final Hello actual = (Hello) Proxy.newProxyInstance(
            Hello.class.getClassLoader(), new Class[]{Hello.class}, invocationHandler
        );

        assertThat(actual.sayHello("Yongju")).isEqualTo("Hello Yongju");
        assertThat(actual.sayHi("Yongju")).isEqualTo("Hi Yongju");
        assertThat(actual.sayThankYou("Yongju")).isEqualTo("Thank You Yongju");
        assertThat(actual.pingPong("Yongju")).isEqualTo("PINGPONG YONGJU");
        assertThat(actual.getClass().toString()).contains("$Proxy");
    }

    @DisplayName("CGLib Proxy - pingPong 메서드만 대문자로 변환")
    @Test
    void cgLibProxyUppercasePingPongMethod() {
        HelloService helloService = new HelloService();

        final MethodMatcher methodMatcher = (method, clazz, args) -> "pingPong".equals(method.getName());
        final MethodInterceptor uppercaseInterceptor = new UppercaseInterceptor(helloService, methodMatcher);
        final HelloService actual = (HelloService) Enhancer.create(HelloService.class, uppercaseInterceptor);

        assertThat(actual.sayHello("Yongju")).isEqualTo("Hello Yongju");
        assertThat(actual.sayHi("Yongju")).isEqualTo("Hi Yongju");
        assertThat(actual.sayThankYou("Yongju")).isEqualTo("Thank You Yongju");
        assertThat(actual.pingPong("Yongju")).isEqualTo("PINGPONG YONGJU");
        assertThat(actual.getClass().toString()).contains("CGLIB$$");
    }
}
