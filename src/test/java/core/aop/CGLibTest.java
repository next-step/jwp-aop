package core.aop;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CGLibTest {

    private HelloCGLibTarget target;

    @BeforeEach
    void setup() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloCGLibTarget.class);
        enhancer.setCallback(new HelloMethodInterceptor(new HelloCGLibTarget()));
        target = (HelloCGLibTarget) enhancer.create();
    }

    @Test
    @DisplayName("CGLib를 사용하여 모든 메소드의 반환 값을 대문자로 변환한다.")
    void dynamicProxy() {
        // given
        final String name = "Test";

        // when
        // then
        assertThat(target.sayHello(name)).isEqualTo("HELLO TEST");
        assertThat(target.sayHi(name)).isEqualTo("HI TEST");
        assertThat(target.sayThankYou(name)).isEqualTo("THANK YOU TEST");
    }
}
