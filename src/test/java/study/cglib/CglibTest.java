package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CglibTest {

    private Enhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
    }

    @Test
    void get_upper_case() {
        enhancer.setCallback(new UpperCaseInterceptor());
        final HelloTarget proxyHelloTarget = (HelloTarget) enhancer.create();

        final String sayHello = proxyHelloTarget.sayHello("jiwon");
        final String sayHi = proxyHelloTarget.sayHi("jiwon");
        final String sayThankYou = proxyHelloTarget.sayThankYou("jiwon");

        assertEquals("HELLO JIWON", sayHello);
        assertEquals("HI JIWON", sayHi);
        assertEquals("THANK YOU JIWON", sayThankYou);
    }
}
