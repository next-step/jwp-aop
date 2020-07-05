package core.aop;

import core.aop.example.SimpleTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("포인트 컷")
class PointCutTest {

    @Test
    void match() throws NoSuchMethodException {
        StringReturnPointCut stringReturnPointCut = new StringReturnPointCut();

        Method retStringMethod = SimpleTarget.class.getDeclaredMethod("getString");
        Method retIntMethod = SimpleTarget.class.getDeclaredMethod("getInt");

        assertThat(stringReturnPointCut.matches(retIntMethod, SimpleTarget.class, null)).isFalse();
        assertThat(stringReturnPointCut.matches(retStringMethod, SimpleTarget.class, null)).isTrue();

    }

    public class StringReturnPointCut implements PointCut {

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object[] arguments) {
            return method.getReturnType().isAssignableFrom(String.class);
        }
    }
}
