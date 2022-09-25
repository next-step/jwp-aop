package core.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JDKAopDynamicProxyTest {

    private Hello dynamicProxyHello;
    private static final String NAME = "Test";

    @BeforeEach
    void setup() throws Exception {
        AbstractAopAdvisor advisor = new PointcutAdvisor(UppercaseAdvice.getInstance(), SayPointCut.getInstance());
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(new HelloTarget(), advisor);
        dynamicProxyHello = (Hello) proxyFactoryBean.getObject();
    }

    @Test
    @DisplayName("Java Dynamic Proxy를 사용하여 모든 메소드의 반환 값을 대문자로 변환한다.")
    void dynamicProxy() {
        // when
        // then
        assertThat(dynamicProxyHello.sayHello(NAME)).isEqualTo("HELLO TEST");
        assertThat(dynamicProxyHello.sayHi(NAME)).isEqualTo("HI TEST");
        assertThat(dynamicProxyHello.sayThankYou(NAME)).isEqualTo("THANK YOU TEST");
    }

    @Test
    @DisplayName("Java Dynamic Proxy를 사용하여 say로 시작하는 메소드의 반환 값을 대문자로 변환한다.")
    void dynamicProxyMethodStartWithSay() {
        // when
        // then
        assertThat(dynamicProxyHello.sayHello(NAME)).isEqualTo("HELLO TEST");
        assertThat(dynamicProxyHello.sayHi(NAME)).isEqualTo("HI TEST");
        assertThat(dynamicProxyHello.sayThankYou(NAME)).isEqualTo("THANK YOU TEST");
        assertThat(dynamicProxyHello.pingpong(NAME)).isEqualTo("ping pong Test");
    }
}
