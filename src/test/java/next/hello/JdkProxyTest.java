package next.hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkProxyTest {
    @DisplayName("Java Dynamic Proxy 사용하여 say로 시작하는 메서드만 대문자로 리턴값 출력")
    @Test
    void toUppercaseWithDynamicProxy() {
        Hello proxiedHello = (Hello) java.lang.reflect.Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class },
                new UpperCaseInvocationHandler(new HelloTarget()));
        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

    @DisplayName("CGLib 사용하여 say로 시작하는 메서드만 대문자로 리턴값 출력")
    @Test
    void toUppercaseWithCGLib() {
        EnhancerWrapper enhancerWrapper = new EnhancerWrapper(HelloTarget.class, new UppercaseMethodInterceptor());
        HelloTarget helloTarget = (HelloTarget) enhancerWrapper.create();

        assertThat(helloTarget.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(helloTarget.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(helloTarget.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(helloTarget.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }
}