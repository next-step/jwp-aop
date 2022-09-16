package core.di.aop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SayMethodPointcutAdvisorTest {

    @DisplayName("Advisor 생성")
    @Test
    void create() {
        final Advice advice = UppercaseAdvice.getInstance();

        final SayMethodPointcutAdvisor sayMethodPointcutAdvisor = new SayMethodPointcutAdvisor(advice);

        assertThat(sayMethodPointcutAdvisor.getAdvice()).isEqualTo(UppercaseAdvice.getInstance());
        assertThat(sayMethodPointcutAdvisor.getPointcut()).isEqualTo(SayMethodPointcut.getInstance());
    }

}
