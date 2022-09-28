package core.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.cglib.HelloTarget;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SayPrefixPointCutTest {
    @Test
    @DisplayName("say 로 시작하는 메서드를 찾는다.")
    void findSayPrefixMethod() throws NoSuchMethodException {
        SayPrefixPointCut sayPrefixPointCut = SayPrefixPointCut.getInstance();
        Method sayHelloMethod = HelloTarget.class.getMethod("sayHello", String.class);
        Method sayHiMethod = HelloTarget.class.getMethod("sayHi", String.class);
        Method sayThankYouMethod = HelloTarget.class.getMethod("sayThankYou", String.class);
        Method pingPongMethod = HelloTarget.class.getMethod("pingPong", String.class);

        assertAll(
                () -> assertThat(sayPrefixPointCut.matches(sayHelloMethod, HelloTarget.class)).isTrue(),
                () -> assertThat(sayPrefixPointCut.matches(sayHiMethod, HelloTarget.class)).isTrue(),
                () -> assertThat(sayPrefixPointCut.matches(sayThankYouMethod, HelloTarget.class)).isTrue(),
                () -> assertThat(sayPrefixPointCut.matches(pingPongMethod, HelloTarget.class)).isFalse()
        );
    }
}