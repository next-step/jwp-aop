package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JDK Dynamic Proxy 학습 테스트")
public class JdkDynamicProxyTest {

    @Test
    @DisplayName("모든 메소드의 리턴값을 대문자로 변환하기 : 프록시 클래스 이용")
    void toUpperCase() {
        Hello hello = new HelloUppercase(new HelloTarget());

        checkReturnValue(hello);
    }

    private void checkReturnValue(Hello hello) {
        assertThat(hello.sayHello("nokchax")).isEqualTo("HELLO NOKCHAX");
        assertThat(hello.sayHi("nokchax")).isEqualTo("HI NOKCHAX");
        assertThat(hello.sayThankYou("nokchax")).isEqualTo("THANK YOU NOKCHAX");
        assertThat(hello.pingPong("nokchax")).isEqualTo("Pong nokchax");
    }

    @Test
    @DisplayName("모든 메소드의 리턴값을 대문자로 변환하기 : 다이나믹 프록시 이용")
    void toUpperCaseWithDynamicProxy() {
        Hello proxyHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new HelloUppercaseInvocationHandler(
                        new HelloTarget(),
                        new MethodNameStartWithMatcher("say")
                )
        );

        checkReturnValue(proxyHello);
    }

    public class HelloUppercase implements Hello {

        private final Hello hello;

        public HelloUppercase(Hello hello) {
            this.hello = hello;
        }

        @Override
        public String sayHello(String name) {
            return hello.sayHello(name).toUpperCase(); //toUpperCase() 중복
        }

        @Override
        public String sayHi(String name) {
            return hello.sayHi(name).toUpperCase();
        }

        @Override
        public String sayThankYou(String name) {
            return hello.sayThankYou(name).toUpperCase();
        }

        @Override
        public String pingPong(String name) {
            return hello.pingPong(name);
        }
    }
}
