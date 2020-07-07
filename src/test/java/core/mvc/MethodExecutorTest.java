package core.mvc;

import core.mvc.exception.InvokeTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("익셉션을 처리하기 위해서 빈과 해당 예외 처리를 위한 메소드를 묶어서 메소드를 실행시키기 위한 클래스")
class MethodExecutorTest {

    @Test
    @DisplayName("메소드 실행 시키기")
    void execute() throws NoSuchMethodException {
        InvokeTest.InvokeTestClass bean = new InvokeTest.InvokeTestClass("test");
        Method method = InvokeTest.InvokeTestClass.class.getDeclaredMethod("getString");

        MethodExecutor methodExecutor = new MethodExecutor(bean, method);

        assertThat(methodExecutor.execute()).isEqualTo("test");
    }
}
