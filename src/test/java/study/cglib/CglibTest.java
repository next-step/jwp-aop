package study.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.methodmatcher.SayPrefixMethodMatcher;

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
        final UppercaseInterceptor uppercaseInterceptor = new UppercaseInterceptor(HelloTarget.class, new SayPrefixMethodMatcher());
        enhancer.setCallback(uppercaseInterceptor);
        final HelloTarget proxyHelloTarget = (HelloTarget) enhancer.create();

        final String sayHello = proxyHelloTarget.sayHello("jiwon");
        final String sayHi = proxyHelloTarget.sayHi("jiwon");
        final String sayThankYou = proxyHelloTarget.sayThankYou("jiwon");
        final String pingpong = proxyHelloTarget.pingpong("jiwon");

        assertEquals("HELLO JIWON", sayHello);
        assertEquals("HI JIWON", sayHi);
        assertEquals("THANK YOU JIWON", sayThankYou);
        assertEquals("Pingpong jiwon", pingpong);
    }
}
