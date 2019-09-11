package study.proxy.jdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import study.proxy.Hello;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTargetTest {

    private static final String NAME = "Test!!!!";

    private Hello proxyInstance;

    @BeforeEach
    void setup() {
        proxyInstance = (Hello) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[]{Hello.class}, new HelloHandler(new HelloTarget())
        );
    }

    @Test
    void sayHello() throws Exception {
        String result = proxyInstance.sayHello(NAME);

        assertThat(result).isEqualTo("HELLO TEST!!!!");
    }

    @Test
    void sayHi() throws Exception {
        String result = proxyInstance.sayHi(NAME);

        assertThat(result).isEqualTo("HI TEST!!!!");
    }

    @Test
    void sayThankYou() throws Exception {
        String result = proxyInstance.sayThankYou(NAME);

        assertThat(result).isEqualTo("THANK YOU TEST!!!!");
    }

}