package aop;

import core.aop.HelloTarget;
import core.aop.PrefixSayMatcher;
import core.aop.UpperMethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CGLIBTest {

    HelloTarget targetProxy;
    @BeforeEach
    void setup() {

        Object proxy = Enhancer.create(HelloTarget.class, new UpperMethodInterceptor(new PrefixSayMatcher()));
        targetProxy = (HelloTarget) proxy;
    }

    @Test
    void cglib_upper_test() {
        Assertions.assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.sayHi("java")).isEqualTo("HI JAVA"),
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.sayHello("java")).isEqualTo("HELLO JAVA"),
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.sayThankYou("java")).isEqualTo("THANK YOU JAVA"),
                () -> org.assertj.core.api.Assertions.assertThat(targetProxy.pinpong("java")).isEqualTo("pingpong java")
        );
    }
}
