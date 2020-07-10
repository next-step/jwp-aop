package core.mvc.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class InvokeTest {

    @Test
    @DisplayName("특정 인스턴스에 대한 invoke 테스트")
    void invoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InvokeTestClass test = new InvokeTestClass("test");
        InvokeTestClass some = new InvokeTestClass("some");

        Method method = test.getClass().getDeclaredMethod("getString");

        String result = (String) method.invoke(test);
        String someResult = (String) method.invoke(some);

        assertThat(result).isEqualTo("test");
        assertThat(someResult).isEqualTo("some");
    }

    public static class InvokeTestClass {
        String string;

        public InvokeTestClass(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }
    }
}
