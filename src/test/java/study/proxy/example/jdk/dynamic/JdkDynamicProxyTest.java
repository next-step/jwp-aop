package study.proxy.example.jdk.dynamic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.proxy.example.Hello;
import study.proxy.example.HelloTarget;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdkDynamicProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(JdkDynamicProxyTest.class);

    Hello proxyInstance;

    @BeforeAll
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

    @Test
    public void pingpong(){
        String pingpong = proxyInstance.pingpong("spring");
        logger.debug(pingpong);
        assertThat(pingpong).isEqualTo("spring");
    }

    @Test
    public void talk() {
        String talk = proxyInstance.talk("spring");
        logger.debug(talk);
        assertThat(talk).isEqualTo("TALK SPRING");
    }
}
