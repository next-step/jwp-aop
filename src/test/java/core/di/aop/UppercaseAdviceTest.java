package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.proxy.HelloService;

class UppercaseAdviceTest {

    @DisplayName("메서드 실행결과를 대문자로 바꾸어 반환한다")
    @Test
    void uppercase() throws Throwable {
        final HelloService object = new HelloService();
        final Method method = object.getClass().getMethod("sayHello", String.class);

        final UppercaseAdvice uppercaseAdvice = UppercaseAdvice.getInstance();
        final String actual = (String) uppercaseAdvice.invoke(object, method, new Object[]{"yongju"});

        assertThat(actual).isEqualTo("HELLO YONGJU");
    }

}
