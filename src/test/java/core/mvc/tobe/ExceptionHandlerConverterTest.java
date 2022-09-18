package core.mvc.tobe;

import core.di.factory.example.MyUserController;
import next.config.MyControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("예외 핸들러 변환기")
class ExceptionHandlerConverterTest {

    @Test
    @DisplayName("싱글톤")
    void instance() {
        assertThatNoException().isThrownBy(ExceptionHandlerConverter::instance);
    }

    @Test
    @DisplayName("controller advice 변환")
    void convert() {
        //given, when
        Map<Class<? extends Throwable>, ExceptionHandlerExecution> executions = ExceptionHandlerConverter.instance()
                .convert(List.of(new MyControllerAdvice()));
        //then
        assertThat(executions).hasSize(1);
    }

    @Test
    @DisplayName("controller advice 아닌 객체는 변환 불가")
    void convert_controller_thrownIllegalArgumentException() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ExceptionHandlerConverter.instance().convert(List.of(new MyUserController())));
    }
}
