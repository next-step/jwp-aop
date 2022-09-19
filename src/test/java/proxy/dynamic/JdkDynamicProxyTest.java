package proxy.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import proxy.cglib.SayMethodMatcher;

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

        assertThat(proxyInstance.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxyInstance.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxyInstance.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
    }

    @DisplayName("pingpong메소드는 대문자로 변환하지 않는다.")
    @Test
    void jdkDynamicProxy2() {

        Hello proxyInstance = (Hello) Proxy.newProxyInstance(JdkDynamicProxyTest.class.getClassLoader(),
            new Class[]{Hello.class}, new DynamicInvocationHandler(helloTarget, matcher));
        assertThat(proxyInstance.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }

}
