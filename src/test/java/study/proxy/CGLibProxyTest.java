package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CGLibProxy 학습 테스트")
public class CGLibProxyTest {
    /*
        Enhancer: 원하는 프록시 객체를 생성할 수 있음
        Callback: 프록시 객체 조작
     */

    @Test
    @DisplayName("인터페이스가 아니라 구현체만 있더라도 프록시를 생성할 수 있다")
    void proxy() {

    }

    private class HelloTarget {
        public String sayHello(String name) {
            return "Hello " + name;
        }

        public String sayHi(String name) {
            return "Hi " + name;
        }

        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
    }
}
