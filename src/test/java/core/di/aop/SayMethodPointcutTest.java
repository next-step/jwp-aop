package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import study.proxy.HelloService;

class SayMethodPointcutTest {

    @DisplayName("메서드명이 say로 시작하는지 확인한다")
    @ParameterizedTest(name = "[{arguments}]")
    @CsvSource({
        "sayHello, true",
        "pingPong, false"
    })
    void method_name_prefix_is_say(String methodName, boolean expected) throws NoSuchMethodException {
        //given
        final Method method = HelloService.class.getMethod(methodName, String.class);
        final SayMethodPointcut sayMethodPointcut = SayMethodPointcut.getInstance();

        //when
        final boolean actual = sayMethodPointcut.matches(method, null);

        //then
        assertThat(actual).isEqualTo(expected);
    }

}
