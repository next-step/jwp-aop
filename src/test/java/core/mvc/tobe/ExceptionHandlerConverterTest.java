package core.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import core.mvc.tobe.mock.MockControllerAdvice;
import core.mvc.tobe.support.ArgumentResolver;
import core.mvc.tobe.support.ExceptionArgumentResolver;
import java.util.Map;
import next.security.RequiredLoginException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionHandlerConverterTest {

    @DisplayName("ControllerAdvice의 ExceptionHandler 애너테이션이 적용된 메서드를 HandlerExecution으로 변환한다")
    @Test
    void convert() {
        final ArgumentResolver argumentResolver = new ExceptionArgumentResolver();
        final ExceptionHandlerConverter exceptionHandlerConverter = new ExceptionHandlerConverter(argumentResolver);
        final MockControllerAdvice controllerAdvice = new MockControllerAdvice();

        final Map<Class<? extends Throwable>, HandlerExecution> actual = exceptionHandlerConverter.convert(controllerAdvice);

        assertAll(
            () -> assertThat(actual).containsKey(RequiredLoginException.class),
            () -> assertThat(actual.get(RequiredLoginException.class)).isInstanceOf(HandlerExecution.class)
        );
    }

}
