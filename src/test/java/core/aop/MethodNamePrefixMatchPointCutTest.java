package core.aop;

import core.aop.example.AopTestService;
import core.aop.pointcut.MethodNamePrefixMatchPointCut;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MethodNamePrefixMatchPointCutTest {

    private AopTestService testService;

    @BeforeEach
    void setUp() {
        testService = new AopTestService();
    }

    @ParameterizedTest
    @DisplayName("MethodNamePrefixMatchPointCut NullAndEmpty 테스트")
    @NullAndEmptySource
    void matchesForNullAndEmpty(String targetPrefix) {
        MethodNamePrefixMatchPointCut pointCut = new MethodNamePrefixMatchPointCut(targetPrefix);

        for (Method method : testService.getClass().getDeclaredMethods()) {
            log.debug("methodName: {}", method.getName());
            assertThat(pointCut.matches(method, method.getDeclaringClass(), method.getParameters())).isFalse();
        }

        assertThat(pointCut.getPrefixSize()).isZero();
    }

    @ParameterizedTest
    @ValueSource(strings = {"to", "get"})
    @DisplayName("MethodNamePrefixMatchPointCut 테스트")
    void matches(String targetPrefix) {
        MethodNamePrefixMatchPointCut pointCut = new MethodNamePrefixMatchPointCut(targetPrefix);

        for (Method method : testService.getClass().getDeclaredMethods()) {
            log.debug("methodName: {}", method.getName());
            if (method.getName().startsWith(targetPrefix)) {
                assertThat(pointCut.matches(method, method.getDeclaringClass(), method.getParameters())).isTrue();
            }
            else {
                assertThat(pointCut.matches(method, method.getDeclaringClass(), method.getParameters())).isFalse();
            }
        }
    }

    @Test
    @DisplayName("MethodNamePrefixMatchPointCut 여러 개의 empty prefix 테스트")
    void matchesManyForNullAndEmpty() {
        List<String[]> parametersList = Arrays.asList(
          new String[] {null, null},
          new String[] {null, ""},
          new String[] {"", null},
          new String[] {"", ""}
        );

        for (String [] parameters : parametersList) {
            MethodNamePrefixMatchPointCut pointCut = new MethodNamePrefixMatchPointCut(parameters[0], parameters[1]);

            for (Method method : testService.getClass().getDeclaredMethods()) {
                log.debug("methodName: {}", method.getName());
                assertThat(pointCut.matches(method, method.getDeclaringClass(), method.getParameters())).isFalse();
            }

            assertThat(pointCut.getPrefixSize()).isZero();
        }
    }


    @ParameterizedTest
    @CsvSource({
        "'to', 'get'",
    })
    @DisplayName("MethodNamePrefixMatchPointCut 두개의 prefix 테스트")
    void matchesMany(String targetPrefix1, String targetPrefix2) {
        MethodNamePrefixMatchPointCut pointCut = new MethodNamePrefixMatchPointCut(targetPrefix1, targetPrefix2);

        for (Method method : testService.getClass().getDeclaredMethods()) {
            log.debug("methodName: {}", method.getName());
            assertThat(pointCut.matches(method, method.getDeclaringClass(), method.getParameters())).isTrue();
        }

        assertThat(pointCut.getPrefixSize()).isEqualTo(2);
    }
}