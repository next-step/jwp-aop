package study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CglibTest {
    private static HelloTarget proxyInstance;

    @BeforeAll
    static void staticSetup() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloMethodInterceptor());

        proxyInstance = (HelloTarget) enhancer.create();
    }

    @Test
    void sayHelloWithProxy() {
        String result = proxyInstance.sayHello("catsbi");

        assertThat(result).isEqualTo("Hello catsbi".toUpperCase());
    }

    @Test
    void sayHiWithProxy() {
        String result = proxyInstance.sayHi("catsbi");

        assertThat(result).isEqualTo("Hi catsbi".toUpperCase());
    }

    @Test
    void sayThankYouWithProxy() {
        String result = proxyInstance.sayThankYou("catsbi");

        assertThat(result).isEqualTo("Thank You catsbi".toUpperCase());
    }
}
