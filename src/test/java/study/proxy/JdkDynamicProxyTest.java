package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JDK Dynamic Proxy 학습 테스트")
public class JdkDynamicProxyTest {

    @Test
    @DisplayName("모든 메소드의 값을 대문자로 변환하기 : 프록시 클래스 이용")
    void toUpperCase() {
        Hello hello = new HelloUppercase(new HelloTarget());

        assertThat(hello.sayHello("nokchax")).isEqualTo("HELLO NOKCHAX");
        assertThat(hello.sayHi("nokchax")).isEqualTo("HI NOKCHAX");
        assertThat(hello.sayThankYou("nokchax")).isEqualTo("THANK YOU NOKCHAX");
    }

    public class HelloUppercase implements Hello {

        private final Hello hello;

        public HelloUppercase(Hello hello) {
            this.hello = hello;
        }

        @Override
        public String sayHello(String name) {
            return hello.sayHello(name).toUpperCase();
        }

        @Override
        public String sayHi(String name) {
            return hello.sayHi(name).toUpperCase();
        }

        @Override
        public String sayThankYou(String name) {
            return hello.sayThankYou(name).toUpperCase();
        }
    }
}
