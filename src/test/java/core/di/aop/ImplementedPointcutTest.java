package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import study.proxy.Hello;
import study.proxy.HelloService;
import study.proxy.HelloTarget;

class ImplementedPointcutTest {

    @DisplayName("Hello 인터페이스의 구현체인지 확인한다")
    @ParameterizedTest(name = "[{arguments}]")
    @MethodSource
    void implemented(Class<?> targetClass, boolean expected) {
        //given
        final ImplementedPointcut implementedPointcut = new ImplementedPointcut(Hello.class);

        //when
        final boolean actual = implementedPointcut.matches(null, targetClass);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> implemented() {
        return Stream.of(
            Arguments.of(HelloTarget.class, true),
            Arguments.of(HelloService.class, false)
        );
    }

}
