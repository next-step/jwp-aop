package core.aop.cglib;

import core.aop.SayMethodMatcher;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpperCaseMethodInterceptorTest {

    @DisplayName("CGLib 학습 테스트")
    @Test
    void cglibTest() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(NoOp.INSTANCE);

        final Object object = enhancer.create();

        final HelloTarget helloTarget = (HelloTarget) object;

        assertThat(helloTarget.sayHello("tester")).isEqualTo("Hello tester");
        assertThat(helloTarget.sayHi("tester")).isEqualTo("Hi tester");
        assertThat(helloTarget.sayThankYou("tester")).isEqualTo("Thank You tester");
        assertThat(helloTarget.pingpong()).isEqualTo("pingpong");
    }

    @DisplayName("모든 메소드의 반환 값을 대문자로 변환")
    @Test
    void UpperCase() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UpperCaseMethodInterceptor(new SayMethodMatcher()));

        final HelloTarget helloTarget = (HelloTarget) enhancer.create();

        assertThat(helloTarget.sayHello("tester")).isEqualTo("HELLO TESTER");
        assertThat(helloTarget.sayHi("tester")).isEqualTo("HI TESTER");
        assertThat(helloTarget.sayThankYou("tester")).isEqualTo("THANK YOU TESTER");
        assertThat(helloTarget.pingpong()).isEqualTo("pingpong");
    }
}