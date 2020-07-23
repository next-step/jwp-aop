package study.proxy.example.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.proxy.example.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CglibProxyTest {
    private static final Logger logger = LoggerFactory.getLogger(CglibProxyTest.class);

    HelloTarget helloTarget;

    @BeforeEach
    public void init(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new MethodCallLogInterceptor());
        helloTarget = (HelloTarget) enhancer.create();

        System.out.println("created proxy");
    }

    @Test
    public void hello(){
        String hello = helloTarget.sayHello("spring");
        logger.debug(hello);
        assertThat(hello).isEqualTo("HELLO SPRING");

    }

    @Test
    public void hi(){
        String hi = helloTarget.sayHi("spring");
        logger.debug(hi);
        assertThat(hi).isEqualTo("HI SPRING");
    }

    @Test
    public void thankYou(){
        String thankYou = helloTarget.sayThankYou("spring");
        logger.debug(thankYou);
        assertThat(thankYou).isEqualTo("THANK YOU SPRING");
    }
}
