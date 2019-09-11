package study.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTargetTest {

    private static final String NAME = "test!!!";

    private HelloTarget helloTarget;

    @BeforeEach
    void setup() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloTargetInterceptor());

        helloTarget = (HelloTarget) enhancer.create();
    }

    @Test
    void sayHello() throws Exception {
        String result = helloTarget.sayHello(NAME);

        assertThat(result).isEqualTo("HELLO TEST!!!");
    }

    @Test
    void sayHi() throws Exception {
        String result = helloTarget.sayHi(NAME);

        assertThat(result).isEqualTo("HI TEST!!!");
    }

    @Test
    void sayThankYou() throws Exception {
        String result = helloTarget.sayThankYou(NAME);

        assertThat(result).isEqualTo("THANK YOU TEST!!!");
    }

}