package study.proxy.example.jdk.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.proxy.example.Hello;
import study.proxy.example.HelloTarget;
import study.proxy.example.normal.HelloTargetTest;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkDynamicProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(JdkDynamicProxyTest.class);

    Hello proxyInstance;

    @BeforeEach
    public void init(){
        proxyInstance = (Hello) Proxy.newProxyInstance(JdkDynamicProxyTest.class.getClassLoader(), new Class[] { Hello.class }, new DynamicInvocationHandler(new HelloTarget()));
        System.out.println("created proxy");
    }

    @Test
    public void hello(){
        String hello = proxyInstance.sayHello("spring");
        logger.debug(hello);
        assertThat(hello).isEqualTo("HELLO SPRING");

    }

    @Test
    public void hi(){
        String hi = proxyInstance.sayHi("spring");
        logger.debug(hi);
        assertThat(hi).isEqualTo("HI SPRING");
    }

    @Test
    public void thankYou(){
        String thankYou = proxyInstance.sayThankYou("spring");
        logger.debug(thankYou);
        assertThat(thankYou).isEqualTo("THANK YOU SPRING");
    }
}
