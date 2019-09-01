package study.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author : yusik
 * @date : 01/09/2019
 */
@DisplayName("CGLib Proxy")
public class CGLibProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(CGLibProxyTest.class);

    @DisplayName("반환 문자열을 대문자로 변환")
    @Test
    void CGLibProxy() {

        // given
        String myName = "kohyusik";
        String expectedSayHello = ("Hello " + myName).toUpperCase();
        String expectedSayHi = ("Hi " + myName).toUpperCase();
        String expectedSayThankYou = ("Thank You " + myName).toUpperCase();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new SayMethodInterceptor());
        HelloTarget target = (HelloTarget) enhancer.create();

        // when
        String sayHello = target.sayHello(myName);
        String sayHi = target.sayHi(myName);
        String sayThankYou = target.sayThankYou(myName);
        logger.debug("{}", sayHello);
        logger.debug("{}", sayHi);
        logger.debug("{}", sayThankYou);

        // then
        assertEquals(expectedSayHello, sayHello);
        assertEquals(expectedSayHi, sayHi);
        assertEquals(expectedSayThankYou, sayThankYou);
    }
}
