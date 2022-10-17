package core.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AnnotationExceptionHandlerMappingTest {

    @Test
    void exceptionEqualsTest() {
        Exception exception = new Exception("test");

        RuntimeException runtimeException1 = new RuntimeException("test1");
        RuntimeException runtimeException2 = new RuntimeException("test2");

        assertThat(exception.getClass()).isNotEqualTo(runtimeException1.getClass());
        assertThat(runtimeException1.getClass()).isEqualTo(runtimeException2.getClass());
    }

}
