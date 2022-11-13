package core.mvc.tobe;


import next.config.MyControllerAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerConverterTest {

    @Test
    @DisplayName("controller advice를 변환한다.")
    void convert() {
        Map<Class<? extends Throwable>, ExceptionHandlerExecution> executions = ExceptionHandlerConverter.getInstance().convert(List.of(new MyControllerAdvice()));

        assertThat(executions).hasSize(1);
    }
}