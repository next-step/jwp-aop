package study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.HelloTarget;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author : yusik
 * @date : 01/09/2019
 */
@DisplayName("CGLib Proxy")
public class CGLibProxyTest {

    @DisplayName("반환 문자열을 대문자로 변환")
    @Test
    void CGLibProxy() {

        // given
        String myName = "kohyusik";
        String expectedSayHello = ("Hello " + myName).toUpperCase();
        String expectedSayHi = ("Hi " + myName).toUpperCase();
        String expectedSayThankYou = ("Thank You " + myName).toUpperCase();
        String expectedPingpong = "Pong " + myName;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new SayMethodInterceptor());
        HelloTarget target = (HelloTarget) enhancer.create();

        // when
        String sayHello = target.sayHello(myName);
        String sayHi = target.sayHi(myName);
        String sayThankYou = target.sayThankYou(myName);
        String pingpong = target.pingpong(myName);

        // then
        assertEquals(expectedSayHello, sayHello);
        assertEquals(expectedSayHi, sayHi);
        assertEquals(expectedSayThankYou, sayThankYou);
        assertEquals(expectedPingpong, pingpong);
    }
}
