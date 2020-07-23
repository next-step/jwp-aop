package study.proxy.example.normal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.proxy.example.HelloTarget;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTargetTest {
    private static final Logger logger = LoggerFactory.getLogger(HelloTargetTest.class);

    HelloTarget target;

    @BeforeEach
    public void init(){
        target = new HelloTarget();
    }

    @Test
    public void hello(){
        String hello = target.sayHello("spring");
        logger.debug(hello);
        assertThat(changeUpperCase(hello)).isEqualTo("HELLO SPRING");

    }

    @Test
    public void hi(){
        String hi = target.sayHi("spring");
        logger.debug(hi);
        assertThat(changeUpperCase(hi)).isEqualTo("HI SPRING");
    }

    @Test
    public void thankYou(){
        String thankYou = target.sayThankYou("spring");
        logger.debug(thankYou);
        assertThat(changeUpperCase(thankYou)).isEqualTo("THANK YOU SPRING");
    }

    public String changeUpperCase(String text){
        return text.toUpperCase();
    }
}
