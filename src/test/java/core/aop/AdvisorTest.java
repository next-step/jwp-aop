package core.aop;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("어드바이저")
class AdvisorTest {

    private static final Advice UPPERCASE_ADVICE = joinpoint -> String.valueOf(joinpoint.proceed()).toUpperCase();
    private static final Pointcut SAY_METHOD_POINT_CUT = (targetClass, method) -> StringUtils.startsWith(method.getName(), "say");
    public static final Advisor SAY_METHOD_UPPERCASE_ADVISOR = Advisor.of(UPPERCASE_ADVICE, SAY_METHOD_POINT_CUT);

    @Test
    @DisplayName("조인 포인트와 어드바이스로 생성")
    void instance() {
        assertThatNoException()
                .isThrownBy(() -> Advisor.of(Joinpoint::proceed, (targetClass, method) -> true));
    }

    @Test
    @DisplayName("조인 포인트, 어드바이스는 필수")
    void instance_null_thrownIllegalArgumentException() {
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> Advisor.of(null, (targetClass, method) -> true)),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> Advisor.of(Joinpoint::proceed, null))
        );
    }

    @Test
    @DisplayName("포인트 컷 일치 여부")
    void matches() {
        assertAll(
                () -> assertThat(SAY_METHOD_UPPERCASE_ADVISOR.matches(AdvisorTest.class, sayMethod())).isTrue(),
                () -> assertThat(SAY_METHOD_UPPERCASE_ADVISOR.matches(AdvisorTest.class, anyMethod())).isFalse()
        );
    }

    @Test
    @DisplayName("대문자 어드바이스 호출")
    void invoke() throws Throwable {
        assertThat(SAY_METHOD_UPPERCASE_ADVISOR.invoke(() -> "any")).isEqualTo("ANY");
    }

    private Method anyMethod() throws NoSuchMethodException {
        return AdvisorTest.class.getDeclaredMethod("any");
    }

    private Method sayMethod() throws NoSuchMethodException {
        return AdvisorTest.class.getDeclaredMethod("sayAny");
    }

    private void sayAny() {
    }

    private void any() {
    }
}
