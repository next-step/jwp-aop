package core.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CGLibTest {

    private static final String NAME = "CGLib";
    private HelloCGLibTarget target;

    @BeforeEach
    void setup() throws Exception {
        AbstractAopAdvisor advisor = new PointcutAdvisor(UppercaseAdvice.getInstance(), SayPointCut.getInstance());
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(new HelloCGLibTarget(), advisor);
        target = (HelloCGLibTarget) proxyFactoryBean.getObject();
    }

    @Test
    @DisplayName("CGLib를 사용하여 모든 메소드의 반환 값을 대문자로 변환한다.")
    void dynamicProxy() {
        // when
        // then
        assertThat(target.sayHello(NAME)).isEqualTo("HELLO CGLIB");
        assertThat(target.sayHi(NAME)).isEqualTo("HI CGLIB");
        assertThat(target.sayThankYou(NAME)).isEqualTo("THANK YOU CGLIB");
    }

    @Test
    @DisplayName("Java Dynamic Proxy를 사용하여 say로 시작하는 메소드의 반환 값을 대문자로 변환한다.")
    void dynamicProxyMethodStartWithSay() {
        // when
        // then
        assertThat(target.sayHello(NAME)).isEqualTo("HELLO CGLIB");
        assertThat(target.sayHi(NAME)).isEqualTo("HI CGLIB");
        assertThat(target.sayThankYou(NAME)).isEqualTo("THANK YOU CGLIB");
        assertThat(target.pingpong(NAME)).isEqualTo("ping pong CGLib");
    }

    @Test
    @DisplayName("결과 값이 String이 아닌 Object 일 경우")
    void resultIsObject() {
        // when
        String name = target.sayObjectTest().getClass().getName();
        // then
        assertThat(name).isEqualTo("java.lang.String");
    }
}
