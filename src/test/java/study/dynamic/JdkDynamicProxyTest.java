package study.dynamic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.cglib.SayMethodMatcher;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkDynamicProxyTest {

    private SayMethodMatcher matcher;
    private HelloTarget helloTarget;

    @BeforeEach
    void setUp() {
        matcher = new SayMethodMatcher();
        helloTarget = new HelloTarget();
    }

    @DisplayName("jdkDynamicProxy로 반환값을 모두 대문자로 변환한다.")
    @Test
    void jdkDynamicProxy() {

        Hello proxyInstance = (Hello) Proxy.newProxyInstance(JdkDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class}, new DynamicInvocationHandler(helloTarget, matcher));

        assertThat(proxyInstance.sayHello("sehee")).isEqualTo("HELLO SEHEE");
        assertThat(proxyInstance.sayHi("sehee")).isEqualTo("HI SEHEE");
        assertThat(proxyInstance.sayThankYou("sehee")).isEqualTo("THANK YOU SEHEE");
    }

    @DisplayName("pingpong메소드는 대문자로 변환하지 않는다.")
    @Test
    void jdkDynamicProxy2() {

        Hello proxyInstance = (Hello) Proxy.newProxyInstance(JdkDynamicProxyTest.class.getClassLoader(),
                new Class[]{Hello.class}, new DynamicInvocationHandler(helloTarget, matcher));

        assertThat(proxyInstance.pingpong("sehee")).isEqualTo("Pong sehee");
    }
}
