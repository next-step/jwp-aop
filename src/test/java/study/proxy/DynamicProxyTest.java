package study.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author : yusik
 * @date : 01/09/2019
 */
@DisplayName("Java Dynamic Proxy")
public class DynamicProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(DynamicProxyTest.class);

    @DisplayName("반환 문자열을 대문자로 변환하는 프록시")
    @Test
    void jdkProxy() {

        // given
        String myName = "kohyusik";
        Hello hello = new HelloTarget();
        String expectedSayHello = ("Hello " + myName).toUpperCase();
        String expectedSayHi = ("Hi " + myName).toUpperCase();
        String expectedSayThankYou = ("Thank You " + myName).toUpperCase();
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class},
                new DynamicInvocationHandler(hello));

        // when
        String sayHello = proxyInstance.sayHello(myName);
        String sayHi = proxyInstance.sayHi(myName);
        String sayThankYou = proxyInstance.sayThankYou(myName);
        logger.debug("{}", sayHello);
        logger.debug("{}", sayHi);
        logger.debug("{}", sayThankYou);

        // then
        assertEquals(expectedSayHello, sayHello);
        assertEquals(expectedSayHi, sayHi);
        assertEquals(expectedSayThankYou, sayThankYou);
    }
}
