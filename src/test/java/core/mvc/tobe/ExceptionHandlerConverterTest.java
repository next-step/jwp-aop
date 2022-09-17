package core.mvc.tobe;

import core.di.factory.example.MyUserController;
import core.mvc.tobe.support.ArgumentResolver;
import core.mvc.tobe.support.HttpRequestArgumentResolver;
import next.config.MyControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("예외 핸들러 변환기")
class ExceptionHandlerConverterTest {

    private static final List<ArgumentResolver> ARGUMENT_RESOLVERS = List.of(new HttpRequestArgumentResolver());

    @Test
    @DisplayName("인자 변환 리스트로 생성")
    void instance() {
        assertThatNoException().isThrownBy(() -> ExceptionHandlerConverter.from(ARGUMENT_RESOLVERS));
    }

    @Test
    @DisplayName("controller advice 변환")
    void convert() {
        //given, when
        Map<Class<? extends Throwable>, ExceptionHandlerExecution> executions = ExceptionHandlerConverter.from(ARGUMENT_RESOLVERS)
                .convert(List.of(new MyControllerAdvice()));
        //then
        assertThat(executions).hasSize(1);
    }

    @Test
    @DisplayName("controller advice 아닌 객체는 변환 불가")
    void convert_controller_thrownIllegalArgumentException() {
        //given
        ExceptionHandlerConverter converter = ExceptionHandlerConverter.from(ARGUMENT_RESOLVERS);
        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> converter.convert(List.of(new MyUserController())));
    }
}
