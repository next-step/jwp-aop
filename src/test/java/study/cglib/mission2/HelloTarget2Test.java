package study.cglib.mission2;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class HelloTarget2Test {

    private HelloTarget2 helloTarget;

    @BeforeEach
    void setUp() {
        var noOpCallback = NoOp.INSTANCE;

        var enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget2Impl.class);
        enhancer.setCallbacks(new Callback[] {noOpCallback, new UpperCaseInterceptor()});
        enhancer.setCallbackFilter(method -> {
            var isSay = method.getName().startsWith("say");
            return Boolean.compare(isSay, false);
        });
        var obj = enhancer.create();

        this.helloTarget = (HelloTarget2)obj;
    }

    @Test
    void sayHello() {
        var actual = helloTarget.sayHello("hello");

        assertThat(actual).isEqualTo("HELLO HELLO");
    }

    @Test
    void sayHi() {
        var actual = helloTarget.sayHi("hello");

        assertThat(actual).isEqualTo("HI HELLO");
    }

    @Test
    void sayThankYou() {
        var actual = helloTarget.sayThankYou("hello");

        assertThat(actual).isEqualTo("THANK YOU HELLO");
    }

    @Test
    void pingpong() {
        var actual = helloTarget.pingpong("hello");

        assertThat(actual).isEqualTo("Pong hello");
    }
}
