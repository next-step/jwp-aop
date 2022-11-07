package study.cglib.mission1;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ByeTargetTest {

    private Hello helloTarget;

    @BeforeEach
    void setUp() {
        var proxyInstance = (Hello)Proxy.newProxyInstance(ByeTargetTest.class.getClassLoader(),
            new Class[] {Hello.class},
            new DynamicInvocationHandler(new HelloTarget()));

        this.helloTarget = proxyInstance;
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

}
