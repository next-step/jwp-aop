package study.cglib;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class MethodTest {

    @DisplayName("Method Modifier test")
    @Test
    void method() {
        List<Method> methods = Arrays.stream(Methods.class.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> !Modifier.isStatic(m.getModifiers()))
                .collect(toList());

        assertThat(methods.size()).isEqualTo(1);
        assertThat(methods.get(0).getName()).isEqualTo("publicMethod");
    }

    public static class Methods {
        public void publicMethod() {

        }

        private void privateMethod() {

        }

        protected void protectedMethod() {

        }

        void defulatMethod() {

        }

        public static void publicStaticMethod() {

        }
    }

}
