package study.jdkproxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/07/20 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class JdkProxyTest {

    Logger logger = LoggerFactory.getLogger(JdkProxyTest.class);

    @Test
    void toUpperCase() {
        Hello helloProxyInstance = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(),
                                                                  new Class[]{Hello.class},
                                                                  new UpperCaseDynamicInvocationHandler(new HelloTarget()));

        logger.debug("Proxy is created");

        assertThat(helloProxyInstance.sayHi("schulz")).isEqualTo("HI SCHULZ");
        assertThat(helloProxyInstance.sayHello("schulz")).isEqualTo("HELLO SCHULZ");
        assertThat(helloProxyInstance.sayThankYou("schulz")).isEqualTo("THANK YOU SCHULZ");
    }
}
